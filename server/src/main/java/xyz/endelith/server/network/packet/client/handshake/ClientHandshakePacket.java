package xyz.endelith.server.network.packet.client.handshake;

import xyz.endelith.cosine.stream.StreamCodec;
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
        System.out.println(protocolVersion);
        System.out.println(serverAddress);
    }

    @Override
    public void handle() {
        System.out.println("I got handshake packet");
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
