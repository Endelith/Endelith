package xyz.endelith.server.network.packet.server.status;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerStatusPongResponsePacket(Long timestamp) implements ServerPacket {
    
    public static final StreamCodec<ServerStatusPongResponsePacket> SERIALIZER = StreamCodec.of(
        StreamCodec.LONG, ServerStatusPongResponsePacket::timestamp,
        ServerStatusPongResponsePacket::new
    );
}
