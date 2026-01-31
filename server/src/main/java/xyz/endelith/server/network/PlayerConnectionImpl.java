package xyz.endelith.server.network;

import io.netty.channel.Channel;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.SocketAddress;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.exception.NetworkException;
import xyz.endelith.server.network.handler.HandshakePacketHandler;
import xyz.endelith.server.network.handler.StatusPacketHandler;
import xyz.endelith.server.network.packet.server.ServerPacket;

public final class PlayerConnectionImpl implements PlayerConnection, UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerConnectionImpl.class);

    private ConnectionState state = ConnectionState.HANDSHAKE;

    private final Channel channel;
    private final MinecraftServerImpl server;
    private final HandshakePacketHandler handshakePacketHandler;
    private final StatusPacketHandler statusPacketHandler;

    public PlayerConnectionImpl(Channel channel, MinecraftServerImpl server) {
        this.channel = Objects.requireNonNull(channel, "channel");
        this.server = Objects.requireNonNull(server, "server");
        this.handshakePacketHandler = new HandshakePacketHandler(this);
        this.statusPacketHandler = new StatusPacketHandler(this);
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
        switch (getState()) {
            case CONFIGURATION -> throw new UnsupportedOperationException("Not implemented yet");
            case LOGIN -> throw new UnsupportedOperationException("Not implemented yet");
            case PLAY -> throw new UnsupportedOperationException("Not implemented yet");
            default -> throw new IllegalStateException("Unexpected state"); 
        }

        // this.channel.close();
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

    public void handleDisconnection() {
        // LOGGER.info("Disconnection {}", this);
    }

    public void sendPacket(ServerPacket packet) {
        try {
            if (!this.channel.isActive()) { 
                return;
            }
            this.channel.writeAndFlush(packet);
        } catch (Throwable t) {
            uncaughtException(Thread.currentThread(), t);
        }
    }

    public ConnectionState getState() {
        return this.state;
    }

    public void setState(ConnectionState state) {
        this.state = Objects.requireNonNull(state, "connection state");
    }

    public HandshakePacketHandler handshakePacketHandler() {
        return this.handshakePacketHandler;
    }

    public StatusPacketHandler statusPacketHandler() {
        return this.statusPacketHandler;
    }
}
