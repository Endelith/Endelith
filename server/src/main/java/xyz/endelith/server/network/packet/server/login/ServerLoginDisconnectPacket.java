package xyz.endelith.server.network.packet.server.login;

import net.kyori.adventure.text.Component;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.codec.component.JsonComponent;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerLoginDisconnectPacket(Component reason) implements ServerPacket {

    public static final StreamCodec<ServerLoginDisconnectPacket> SERIALIZER = StreamCodec.of(
        JsonComponent.STREAM_CODEC, ServerLoginDisconnectPacket::reason,
        ServerLoginDisconnectPacket::new
    );
}
