package xyz.endelith.server.network.handler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import xyz.endelith.event.Events;
import xyz.endelith.event.player.PlayerPreLoginEvent;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginHelloPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginFinishedPacket;
import xyz.endelith.util.profile.GameProfile;

public final class LoginPacketHandler extends PacketHandler {

    public LoginPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientLoginAcknowledgedPacket packet) {
        this.connection.setState(ConnectionState.CONFIGURATION);
    }

    public void handle(ClientLoginHelloPacket packet) {
        GameProfile profile = new GameProfile(offlineUuid(packet.username()), packet.username());
        PlayerPreLoginEvent event = new PlayerPreLoginEvent(this.connection, profile);

        this.server.pluginEventManager().fire(Events.PLAYER_PRE_LOGIN, event);
        this.connection.sendPacket(new ServerLoginFinishedPacket(event.profile()));
    }

    private static UUID offlineUuid(String username) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
    }
}
