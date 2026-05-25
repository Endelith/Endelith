package xyz.endelith.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.server.MinecraftServerImpl;

public final class NetworkManager extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);

    public static final String LENGTH_DECODER = "length-decoder";
    public static final String COMPRESSOR_DECODER = "compressor-decoder";
    public static final String CIPHER_DECODER = "cipher-decoder";
    public static final String PACKET_DECODER = "packet-decoder";
    public static final String PACKET_HANDLER = "packet-handler";
    public static final String PACKET_ENCODER = "packet-encoder";
    public static final String CIPHER_ENCODER = "cipher-encoder";
    public static final String COMPRESSOR_ENCODER = "compressor-encoder";
    public static final String LENGTH_ENCODER = "length-encoder";

    private final MinecraftServerImpl server;
    private final ServerBootstrap bootstrap;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private Channel channel;

    public NetworkManager(MinecraftServerImpl server) {
        this.server = Objects.requireNonNull(server, "server");

        this.bossGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        this.workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        this.bootstrap = new ServerBootstrap()
            .group(this.bossGroup, this.workerGroup)
            .channel(NioServerSocketChannel.class)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(this);
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        PlayerConnectionImpl connection = new PlayerConnectionImpl(ch, this.server);
        ChannelPipeline pipeline = ch.pipeline();

    }

    public void bind() {
        if (this.channel != null) {
            throw new IllegalStateException("The network manager has already been started");
        }

        // ServerConfiguration configuration = server.configuration();
        String address = "localhost";
        int port = 25565;

        this.channel = this.bootstrap.bind(address, port).awaitUninterruptibly().channel();
        LOGGER.info("Listening on {}", this.channel.localAddress());
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
