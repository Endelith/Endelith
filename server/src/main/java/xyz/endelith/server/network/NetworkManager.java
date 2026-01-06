package xyz.endelith.server.network;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.IoHandlerFactory;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.pipeline.decoder.PacketDecoder;
import xyz.endelith.server.network.pipeline.decoder.PacketLenghtDecoder;
import xyz.endelith.server.network.pipeline.encoder.PacketEncoder;
import xyz.endelith.server.network.pipeline.encoder.PacketLenghtEncoder;
import xyz.endelith.server.network.pipeline.handler.PacketHandler;

@NullMarked
public class NetworkManager extends ChannelInitializer<SocketChannel> {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);
 
    private static final String LENGTH_DECODER = "length-decoder";
    private static final String PACKET_DECODER = "packet-decoder";
    private static final String PACKET_HANDLER = "packet-handler";
    private static final String PACKET_ENCODER = "packet-encoder";
    private static final String LENGTH_ENCODER = "length-encoder";

    private final MinecraftServerImpl server;
    private final ServerBootstrap bootstrap;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private Channel channel;

    public NetworkManager(MinecraftServerImpl server) {
        IoHandlerFactory factory = NioIoHandler.newFactory(); //TODO: EPOLL?
        this.server = Objects.requireNonNull(server, "server");

        this.bossGroup = new MultiThreadIoEventLoopGroup(factory);
        this.workerGroup = new MultiThreadIoEventLoopGroup(factory); 
        
        this.bootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(this);   
    }

    public void bind() {
        if (channel != null)
            throw new IllegalStateException("The network manager has already been started");
       
        ServerConfiguration configuration = server.serverConfiguration();
        String address = configuration.serverAddress();
        int port = configuration.serverPort();

        channel = bootstrap.bind(address, port).awaitUninterruptibly().channel();
        LOGGER.info("Listening on {}", this.channel.localAddress());
    }
 
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        var connection = new PlayerConnectionImpl(ch, server);
        var pipeline = ch.pipeline();

        pipeline.addFirst(PACKET_ENCODER, new PacketEncoder(connection));
        pipeline.addBefore(PACKET_ENCODER, LENGTH_ENCODER, new PacketLenghtEncoder(connection));
        
        pipeline.addFirst(LENGTH_DECODER, new PacketLenghtDecoder(connection));
        pipeline.addAfter(LENGTH_DECODER, PACKET_DECODER, new PacketDecoder(connection));
        pipeline.addAfter(PACKET_DECODER, PACKET_HANDLER, new PacketHandler(connection)); 
    }
 
    public void shutdown() {
        if (channel == null) return;
        channel.close().awaitUninterruptibly();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel = null;
    }
}
