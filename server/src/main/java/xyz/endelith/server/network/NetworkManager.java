package xyz.endelith.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.netty.decoder.PacketDecoder;
import xyz.endelith.server.network.netty.decoder.PacketLenghtDecoder;
import xyz.endelith.server.network.netty.transport.NettyTransportType;

public class NetworkManager extends ChannelInitializer<SocketChannel> {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);

    public static final String LENGTH_DECODER  = "length-decoder";
    public static final String COMPRESSOR_DECODER = "compressor-decoder";
    public static final String CIPHER_DECODER  = "cipher-decoder";
    public static final String PACKET_DECODER  = "packet-decoder";
    public static final String PACKET_HANDLER  = "packet-handler";
    public static final String PACKET_ENCODER  = "packet-encoder";
    public static final String CIPHER_ENCODER  = "cipher-encoder";
    public static final String COMPRESSOR_ENCODER = "compressor-encoder";
    public static final String LENGTH_ENCODER  = "length-encoder"; 
 
    private final MinecraftServerImpl server;
    private final ServerBootstrap bootstrap;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private Channel channel;

    public NetworkManager(MinecraftServerImpl server) {
        this.server = Objects.requireNonNull(server, "server");

        NettyTransportType transport = server.configuration().selector().transportType();
        if (!transport.isAvailable()) {
            NettyTransportType oldTransport = transport;
            transport = NettyTransportType.select();
            LOGGER.warn(
                "The netty transport specified - {} - is not available, falling back to {}...",
                oldTransport, transport
            );
        }

        this.bossGroup = transport.createEventLoop();
        this.workerGroup = transport.createEventLoop();

        this.bootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(transport.getSocketChannelClass())
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(this);   
    }

    public void bind() {
        if (this.channel != null) {
            throw new IllegalStateException("The network manager has already been started");
        }

        ServerConfiguration configuration = this.server.configuration();
        String address = configuration.address();
        int port = configuration.port();

        this.channel = this.bootstrap.bind(address, port).awaitUninterruptibly().channel();
        LOGGER.info("Listening on {}", this.channel.localAddress());
    }
 
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        var connection = new PlayerConnectionImpl(ch, server);
        var pipeline = ch.pipeline();

        // pipeline.addFirst(PACKET_ENCODER, new PacketEncoder(connection));
        // pipeline.addBefore(PACKET_ENCODER, LENGTH_ENCODER, new PacketLenghtEncoder(connection));
        
        pipeline.addFirst(LENGTH_DECODER, new PacketLenghtDecoder());
        pipeline.addAfter(LENGTH_DECODER, PACKET_DECODER, new PacketDecoder(connection));
        // pipeline.addAfter(PACKET_DECODER, PACKET_HANDLER, new PacketHandler(connection)); 
    }
 
    public void shutdown() {
        if (this.channel == null) {
            return;
        }

        this.channel.close().awaitUninterruptibly();
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
        this.channel = null;
    }
}
