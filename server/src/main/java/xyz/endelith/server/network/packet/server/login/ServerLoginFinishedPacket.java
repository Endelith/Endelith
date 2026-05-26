package xyz.endelith.server.network.packet.server.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;
import xyz.endelith.util.profile.GameProfile;

public record ServerLoginFinishedPacket(GameProfile profile) implements ServerPacket {

    private static final StreamCodec<GameProfile.Property> PROPERTY_STREAM_CODEC = StreamCodec.of(
            StreamCodec.STRING, GameProfile.Property::name,
            StreamCodec.STRING, GameProfile.Property::value,
            StreamCodec.STRING.optional(), GameProfile.Property::signature,
            GameProfile.Property::new
    );

    private static final StreamCodec<GameProfile> GAME_PROFILE_STREAM_CODEC = StreamCodec.of(
            StreamCodec.UUID, GameProfile::uuid,
            StreamCodec.STRING, GameProfile::username,
            PROPERTY_STREAM_CODEC.list(16), GameProfile::properties,
            GameProfile::new
    );

    public static final StreamCodec<ServerLoginFinishedPacket> STREAM_CODEC = StreamCodec.of(
            GAME_PROFILE_STREAM_CODEC, ServerLoginFinishedPacket::profile,
            ServerLoginFinishedPacket::new
    );
}
