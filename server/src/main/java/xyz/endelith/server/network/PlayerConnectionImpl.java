package xyz.endelith.server.network;

import static xyz.endelith.server.network.NetworkManager.*;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.velocitypowered.natives.compression.VelocityCompressor;
import com.velocitypowered.natives.encryption.VelocityCipherFactory;
import com.velocitypowered.natives.util.Natives;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.kyori.adventure.text.Component;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.exception.NetworkException;
import xyz.endelith.server.network.handler.HandshakePacketHandler;
import xyz.endelith.server.network.handler.LoginPacketHandler;
import xyz.endelith.server.network.handler.StatusPacketHandler;
import xyz.endelith.server.network.netty.decoder.CipherDecoder;
import xyz.endelith.server.network.netty.decoder.CompressionDecoder;
import xyz.endelith.server.network.netty.encoder.CipherEncoder;
import xyz.endelith.server.network.netty.encoder.CompressionEncoder;
import xyz.endelith.server.network.packet.server.ServerPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginDisconnectPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginSetCompressionPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginSuccessPacket;
import xyz.endelith.util.profile.GameProfile;;

public class PlayerConnectionImpl implements PlayerConnection, Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerConnectionImpl.class);

    private ConnectionState state = ConnectionState.HANDSHAKE;
    private String username;

    private final Channel channel;
    private final MinecraftServerImpl server;

    private final HandshakePacketHandler handshakePacketHandler;
    private final StatusPacketHandler statusPacketHandler;
    private final LoginPacketHandler loginPacketHandler;

    public PlayerConnectionImpl(Channel channel, MinecraftServerImpl server) {
        this.channel = Objects.requireNonNull(channel, "channel");
        this.server = Objects.requireNonNull(server, "server");
        this.handshakePacketHandler = new HandshakePacketHandler(this);
        this.statusPacketHandler = new StatusPacketHandler(this);
        this.loginPacketHandler = new LoginPacketHandler(this);
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
        switch (getState()) {
            case CONFIGURATION -> throw new UnsupportedOperationException("Not implemented yet");
            case LOGIN -> sendPacket(new ServerLoginDisconnectPacket(reason));
            case PLAY -> throw new UnsupportedOperationException("Not implemented yet");
            default -> throw new IllegalStateException("Unexpected state"); 
        }

        channel.close();
    }
 
    @Override
    public MinecraftServerImpl server() {
        return server;
    }
 
    @Override
    public SocketAddress address() {
        return channel.remoteAddress();
    }

    public void handleDisconnection() {
        //LOGGER.info("Disconnection {}", this);
    }

    public void sendPacket(ServerPacket packet) {
        try {
            if (!channel.isActive()) return;
            channel.writeAndFlush(packet);
        } catch (Throwable throwable) {
            uncaughtException(Thread.currentThread(), throwable);
        }
    }

    public void enableEncryption(byte[] secret) {
        SecretKeySpec secretKey = new SecretKeySpec(secret, "AES");
        VelocityCipherFactory cipherFactory = Natives.cipher.get(); 
        
        try {
            channel.pipeline()
                .addBefore(LENGTH_DECODER, CIPHER_DECODER,
                    new CipherDecoder(this, cipherFactory.forDecryption(secretKey)))
                .addBefore(LENGTH_ENCODER, CIPHER_ENCODER, 
                    new CipherEncoder(this, cipherFactory.forEncryption(secretKey))); 
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Failed to enable encryption", e);
        }
    }
    
    public void initPlayer(GameProfile profile) {
        if (profile == null) {
            UUID offlineUUID = UUID.nameUUIDFromBytes(
                ("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8)
            );
            profile = new GameProfile(offlineUUID, username);
        }

        setupCompression();
        sendPacket(new ServerLoginSuccessPacket(profile));
        //TODO: Player object? 
    }

    private void setupCompression() {
        int threshold = server.configuration().compressionThreshold();
        if (threshold < 0) return;
    
        VelocityCompressor compressor =
            Natives.compress.get().create(
                server.configuration().compressionLevel()
            );
    
        sendPacket(new ServerLoginSetCompressionPacket(threshold));
    
        channel.pipeline()
            .addAfter(LENGTH_DECODER, COMPRESSOR_DECODER,
                new CompressionDecoder(this, compressor)
            )
            .addAfter(LENGTH_ENCODER, COMPRESSOR_ENCODER,
                new CompressionEncoder(this, compressor)
            );
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @Nullable String getUsername() {
        return username;
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

    public StatusPacketHandler statusPacketHandler() {
        return statusPacketHandler;
    }

    public LoginPacketHandler loginPacketHandler() {
        return loginPacketHandler;
    }
}
