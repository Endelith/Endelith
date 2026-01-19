package xyz.endelith.server.network.packet.server.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.codec.profile.GameProfileCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;
import xyz.endelith.util.profile.GameProfile;

public record ServerLoginSuccessPacket(GameProfile profile) implements ServerPacket {

    public final static StreamCodec<ServerLoginSuccessPacket> SERIALIZER = StreamCodec.of(
        GameProfileCodec.STREAM_CODEC, ServerLoginSuccessPacket::profile,
        ServerLoginSuccessPacket::new
    );
}
