package xyz.endelith.server.network.handler;

import xyz.endelith.server.configuration.ServerConfigurationImpl;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakePacket;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakePacket.Intent;

public final class HandshakePacketHandler extends PacketHandler {

    public HandshakePacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientHandshakePacket packet) {
        switch (packet.intent()) {
            case STATUS -> connection.setState(ConnectionState.STATUS);
            case LOGIN, TRANSFER -> {
                connection.setState(ConnectionState.LOGIN);
                ServerConfigurationImpl configuration = server.configuration();

                if (packet.intent() == Intent.TRANSFER) {
                    if (!configuration.transfersAllowed()) {
                        connection.disconnect(configuration.transfersNotAllowedMessage());
                    }
                }

                if (packet.protocolVersion() != server.protocolVersion()) {
                    connection.disconnect(configuration.unsupportedVersionMessage());
                }
            }
        }
    }
}
