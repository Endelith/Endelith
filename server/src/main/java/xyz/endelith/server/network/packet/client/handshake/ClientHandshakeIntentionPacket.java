package xyz.endelith.server.network.packet.client.handshake;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.configuration.ServerConfigurationImpl;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientHandshakeIntentionPacket(
        int protocolVersion,
        String address,
        int port,
        Intent intent
) implements ClientPacket {

    public static final StreamCodec<ClientHandshakeIntentionPacket> STREAM_CODEC = StreamCodec.of(
            StreamCodec.VAR_INT, ClientHandshakeIntentionPacket::protocolVersion,
            StreamCodec.STRING, ClientHandshakeIntentionPacket::address,
            StreamCodec.UNSIGNED_SHORT, ClientHandshakeIntentionPacket::port,
            StreamCodec.VAR_INT.transform(Intent::fromId, Intent::id), ClientHandshakeIntentionPacket::intent,
            ClientHandshakeIntentionPacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        switch (intent()) {
            case STATUS -> connection.setState(ConnectionState.STATUS);
            case LOGIN, TRANSFER -> {
                connection.setState(ConnectionState.LOGIN);
                ServerConfigurationImpl configuration = connection.server().configuration();

                if (intent() == Intent.TRANSFER) {
                    if (!configuration.transfersAllowed()) {
                        connection.disconnect(configuration.transfersNotAllowedMessage());
                    }
                }

                if (protocolVersion() != connection.server().protocolVersion()) {
                    connection.disconnect(configuration.unsupportedVersionMessage());
                }
            }
            default -> throw new IllegalStateException("Unexpected intent");
        }
    }

    public enum Intent {
        STATUS,
        LOGIN,
        TRANSFER;

        public static Intent fromId(int id) {
            return switch (id) {
                case 1 -> STATUS;
                case 2 -> LOGIN;
                case 3 -> TRANSFER;
                default -> throw new IllegalArgumentException("Unknown connection intent: " + id);
            };
        }

        public int id() {
            return ordinal() + 1;
        }
    }
}
