package xyz.endelith.server.network.packet.client.handshake;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;
 
public record ClientHandshakePacket(
    int protocolVersion, 
    String serverAddress,
    int serverPort,
    Intent intent
) implements ClientPacket {

    public static final StreamCodec<ClientHandshakePacket> STREAM_CODEC = StreamCodec.of(
        StreamCodec.VAR_INT, ClientHandshakePacket::protocolVersion,
        StreamCodec.STRING, ClientHandshakePacket::serverAddress,
        StreamCodec.UNSIGNED_SHORT, ClientHandshakePacket::serverPort,
        StreamCodec.VAR_INT.transform(Intent::fromId, Intent::id), ClientHandshakePacket::intent,
        ClientHandshakePacket::new
    );

    public ClientHandshakePacket {
        if (serverAddress.length() > maxHandshakeLength()) {
            throw new IllegalArgumentException(String.format(
                "Server address too long: %s",
                serverAddress.length()
            ));
        }
    }

    private static int maxHandshakeLength() {
        // TODO: Bungeecord?
        return 255;
    }

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.handshakePacketHandler().handle(this);
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
