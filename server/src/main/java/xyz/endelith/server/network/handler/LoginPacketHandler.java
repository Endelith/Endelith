package xyz.endelith.server.network.handler;

import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;

import net.kyori.adventure.text.Component;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.login.ClientLoginEncryptionResponsePacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginStartPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginEncryptionRequestPacket;
import xyz.endelith.server.util.EncryptionUtil;
import xyz.endelith.server.util.MojangUtil;
import xyz.endelith.util.profile.GameProfile;

public final class LoginPacketHandler extends PacketHandler {

    private byte[] verifyToken;

    public LoginPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientLoginStartPacket packet) {
        connection.setUsername(packet.username());
        if (server.configuration().onlineMode()) {
            byte[] publicKey = server.keyPair().getPublic().getEncoded();
            this.verifyToken = new byte[4];
            ThreadLocalRandom.current().nextBytes(verifyToken);
            connection.sendPacket(new ServerLoginEncryptionRequestPacket("", publicKey, verifyToken, true));
        } else {
            connection.initPlayer(null);
        }
    }

    public void handle(ClientLoginEncryptionResponsePacket packet) {
        byte[] decryptedVerifyToken = EncryptionUtil.decryptRsa(server.keyPair(), packet.verifytoken());
        
        if (!MessageDigest.isEqual(decryptedVerifyToken, verifyToken)) {
            connection.disconnect(Component.text("Verify token does not match!"));
        }

        byte[] decryptedSecret = EncryptionUtil.decryptRsa(server.keyPair(), packet.sharedKey());
        String serverId = EncryptionUtil.generateServerId(server.keyPair(), decryptedSecret);
        GameProfile profile = MojangUtil.hasJoined(connection.getUsername(), serverId);
        connection.enableEncryption(decryptedSecret);
        connection.initPlayer(profile);
    }
}
