package xyz.endelith.server.network;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.exception.NetworkException;

public final class PlayerConnectionImpl implements PlayerConnection, Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerConnectionImpl.class);

    private ConnectionState state = ConnectionState.HANDSHAKE;

    private final Channel channel;
    private final MinecraftServerImpl server;

    public PlayerConnectionImpl(SocketChannel channel, MinecraftServerImpl server) {
        this.channel = Objects.requireNonNull(channel, "channel");
        this.server = Objects.requireNonNull(server, "server");
    }

    @Override
    public MinecraftServerImpl server() {
        return this.server;
    }

    @Override
    public void disconnect(Component reason) {
        this.channel.close();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof NetworkException) {
            return;
        }

        this.channel.close();

        NetworkException networkException = new NetworkException(this, e);
        LOGGER.error("A network error occurred in thread {}", t.getName(), networkException);
    }

    public ConnectionState state() {
        return this.state;
    }

    public void setState(ConnectionState state) {
        this.state = Objects.requireNonNull(state, "state");
    }
}
