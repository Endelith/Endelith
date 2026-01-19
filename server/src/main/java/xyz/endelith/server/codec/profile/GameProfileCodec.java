package xyz.endelith.server.codec.profile;

import java.util.UUID;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.util.MojangUtil;
import xyz.endelith.util.profile.GameProfile;

public final class GameProfileCodec {

    private GameProfileCodec() {}

    public static final StreamCodec<GameProfile> STREAM_CODEC = StreamCodec.of(
        StreamCodec.UUID, GameProfile::uuid,
        StreamCodec.STRING, GameProfile::name,
        PropertyCodec.STREAM_CODEC.list(), GameProfile::properties,
        GameProfile::new
    );

    public static final Codec<GameProfile> STRING_CODEC = StructCodec.of(
        "id", Codec.STRING.transform(MojangUtil::fromMojang, UUID::toString), GameProfile::uuid,
        "name", Codec.STRING, GameProfile::name,
        "properties", PropertyCodec.STRING_CODEC.list().optional(), GameProfile::properties,
        GameProfile::new
    );
}
