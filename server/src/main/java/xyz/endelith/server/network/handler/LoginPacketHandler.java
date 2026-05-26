package xyz.endelith.server.network.handler;

import xyz.endelith.server.network.PlayerConnectionImpl;

public final class LoginPacketHandler extends PacketHandler {

    public LoginPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }
}
