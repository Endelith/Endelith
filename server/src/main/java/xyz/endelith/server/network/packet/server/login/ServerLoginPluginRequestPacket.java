package xyz.endelith.server.network.packet.server.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerLoginPluginRequestPacket(int messageId, String channel, byte[] data) implements ServerPacket {

    public static final StreamCodec<ServerLoginPluginRequestPacket> SERIALIZER = StreamCodec.of(
        StreamCodec.VAR_INT, ServerLoginPluginRequestPacket::messageId,
        StreamCodec.STRING, ServerLoginPluginRequestPacket::channel,
        StreamCodec.BYTE_ARRAY, ServerLoginPluginRequestPacket::data,
        ServerLoginPluginRequestPacket::new
    );
}
