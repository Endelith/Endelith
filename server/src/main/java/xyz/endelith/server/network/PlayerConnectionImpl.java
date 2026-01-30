package xyz.endelith.server.network;

import io.netty.channel.Channel;
import java.net.SocketAddress;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.server.MinecraftServerImpl;

public final class PlayerConnectionImpl implements PlayerConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerConnectionImpl.class);

    private ConnectionState state = ConnectionState.HANDSHAKE;

    private final Channel channel;
    private final MinecraftServerImpl server;

    public PlayerConnectionImpl(Channel channel, MinecraftServerImpl server) {
        this.channel = Objects.requireNonNull(channel, "channel");
        this.server = Objects.requireNonNull(server, "server");
    }

    @Override
    public MinecraftServerImpl server() {
        return this.server;
    }

    @Override
    public SocketAddress address() {
        return this.channel.remoteAddress();
    }

    @Override
    public void disconnect(Component reason) {
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
    }

    public ConnectionState getState() {
        return this.state;
    }

    public void setState(ConnectionState state) {
        this.state = Objects.requireNonNull(state, "connection state");
    }
}
