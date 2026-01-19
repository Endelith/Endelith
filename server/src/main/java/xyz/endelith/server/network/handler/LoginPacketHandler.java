package xyz.endelith.server.network.handler;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.kyori.adventure.text.Component;
import xyz.endelith.event.events.player.PlayerPreLoginEvent;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginEncryptionResponsePacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginPluginResponsePacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginStartPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginEncryptionRequestPacket;
import xyz.endelith.server.network.plugin.LoginPluginMessageProcessorImpl;
import xyz.endelith.server.util.EncryptionUtil;
import xyz.endelith.server.util.MojangUtil;
import xyz.endelith.util.profile.GameProfile;

public final class LoginPacketHandler extends PacketHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPacketHandler.class);

    private static final Component VERIFY_TOKEN_MISMATCH = Component.text("Verify token does not match!");
    private static final Component ACCOUNT_VERIFY_FAILED = Component.text("Failed to verify account");
    private static final Component LOGIN_PLUGIN_TIMEOUT = Component.text("Login plugin timeout"); 
    private static final Component PRE_LOGIN_EVENT_ERROR = Component.text("An error occurred during pre-login event");
    private static final Component INVALID_PLUGIN_RESPONSE = Component.text("Invalid plugin response");

    private byte[] verifyToken;
    private final LoginPluginMessageProcessorImpl pluginMessageProcessor;

    public LoginPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
        this.pluginMessageProcessor = new LoginPluginMessageProcessorImpl(connection);
    }

    public void handle(ClientLoginStartPacket packet) {
        connection.setUsername(packet.username());
        if (server.configuration().onlineMode()) {
            byte[] publicKey = server.keyPair().getPublic().getEncoded();
            this.verifyToken = new byte[4];
            ThreadLocalRandom.current().nextBytes(verifyToken);
            connection.sendPacket(new ServerLoginEncryptionRequestPacket("", publicKey, verifyToken, true));
        } else {
            UUID offlineUUID = UUID.nameUUIDFromBytes(
                ("OfflinePlayer:" + connection.getUsername()).getBytes(StandardCharsets.UTF_8)
            );
            GameProfile initialProfile = new GameProfile(offlineUUID, connection.getUsername());
            firePreLoginEvent(initialProfile);
        }
    }

    public void handle(ClientLoginEncryptionResponsePacket packet) {
        byte[] decryptedVerifyToken = EncryptionUtil.decryptRsa(server.keyPair(), packet.verifytoken());
        
        if (!MessageDigest.isEqual(decryptedVerifyToken, verifyToken)) {
            connection.disconnect(VERIFY_TOKEN_MISMATCH);
            return;
        }

        byte[] decryptedSecret = EncryptionUtil.decryptRsa(server.keyPair(), packet.sharedKey());
        String serverId = EncryptionUtil.generateServerId(server.keyPair(), decryptedSecret);

        GameProfile profile;
        try {
            profile = MojangUtil.hasJoined(connection.getUsername(), serverId);
        } catch (Exception e) {
            connection.disconnect(ACCOUNT_VERIFY_FAILED);
            return;
        }

        connection.enableEncryption(decryptedSecret);
        firePreLoginEvent(profile);
    }

    public void handle(ClientLoginPluginResponsePacket packet) {
        try {
            pluginMessageProcessor.handleResponse(packet.messageId(), packet.data());
        } catch (Exception e) {
            connection.disconnect(INVALID_PLUGIN_RESPONSE);
        }
    }

    public void handle(ClientLoginAcknowledgedPacket packet) {

    }

    private void firePreLoginEvent(GameProfile initialProfile) { 
        PlayerPreLoginEvent event = new PlayerPreLoginEvent(connection, initialProfile, pluginMessageProcessor);
       
        try {
            server.eventManager().call(event);
        } catch (Throwable t) {
            connection.disconnect(PRE_LOGIN_EVENT_ERROR);
            LOGGER.error("An error occurred during handling of the player pre login event", t);
            return;
        }

        GameProfile finalProfile = event.getProfile();

        try {
            pluginMessageProcessor.awaitReplies(5000, TimeUnit.MILLISECONDS);
        } catch (Throwable t) {
            connection.disconnect(LOGIN_PLUGIN_TIMEOUT);
            LOGGER.error("Error getting replies for login plugin messages", t);
            return;
        }

        connection.initPlayer(finalProfile);
    }
}
