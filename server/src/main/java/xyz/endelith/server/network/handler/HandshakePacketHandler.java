package xyz.endelith.server.network.handler;

import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakePacket;

public final class HandshakePacketHandler extends PacketHandler {

    public HandshakePacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientHandshakePacket packet) {
        switch (packet.intent()) {
            case STATUS -> connection.setState(ConnectionState.STATUS);
            case LOGIN, TRANSFER -> throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
