package xyz.endelith.server.network;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.exception.NetworkException;
import xyz.endelith.server.network.handler.HandshakePacketHandler;
import xyz.endelith.server.network.packet.server.ServerPacket;

public final class PlayerConnectionImpl implements PlayerConnection, Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerConnectionImpl.class);

    private ConnectionState state = ConnectionState.HANDSHAKE;

    private final Channel channel;
    private final MinecraftServerImpl server;

    private final HandshakePacketHandler handshakePacketHandler;

    public PlayerConnectionImpl(
        Channel channel, 
        MinecraftServerImpl server, 
        HandshakePacketHandler handshakePacketHandler
    ) {
        this.channel = Objects.requireNonNull(channel, "channel");
        this.server = Objects.requireNonNull(server, "server");
        this.handshakePacketHandler = Objects.requireNonNull(handshakePacketHandler, "handshake packet handler");
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof NetworkException) 
            return;
        
        channel.close();

        NetworkException networkException = new NetworkException(this, e);
        LOGGER.error("A network error occurred in thread {}", t.getName(), networkException);
    }

    @Override
    public void disconnect(Component reason) {
        return;
        //switch (getState()) {
        //    case CONFIGURATION -> throw new UnsupportedOperationException("Not implemented yet");
        //    case LOGIN -> sendPacket(new ClientLoginDisconnectPacket(reason));
        //    case PLAY -> throw new UnsupportedOperationException("Not implemented yet");
        //    default -> throw new IllegalStateException("Unexpected state"); 
        //}
    }
 
    @Override
    public MinecraftServerImpl server() {
        return server;
    }

    public void sendPacket(ServerPacket packet) {
        try {
            if (!channel.isActive()) return;
            channel.writeAndFlush(packet);
        } catch (Throwable throwable) {
            uncaughtException(Thread.currentThread(), throwable);
        }
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }

    public ConnectionState getState() {
        return state;
    }

    public HandshakePacketHandler handshakePacketHandler() {
        return handshakePacketHandler;
    }
}
