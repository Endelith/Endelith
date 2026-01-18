package xyz.endelith.server.network.handler;

import net.kyori.adventure.text.Component;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.login.ClientLoginStartPacket;

public final class LoginPacketHandler extends PacketHandler {

    public LoginPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientLoginStartPacket packet) {
        connection.disconnect(Component.text("You can't join the server because i don't want you"));
    }
}
