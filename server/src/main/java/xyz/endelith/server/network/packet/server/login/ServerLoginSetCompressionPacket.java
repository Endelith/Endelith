package xyz.endelith.server.network.packet.server.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerLoginSetCompressionPacket(int threshold) implements ServerPacket {

    public static final StreamCodec<ServerLoginSetCompressionPacket> SERIALIZER = StreamCodec.of(
        StreamCodec.VAR_INT, ServerLoginSetCompressionPacket::threshold,
        ServerLoginSetCompressionPacket::new
    );
}
