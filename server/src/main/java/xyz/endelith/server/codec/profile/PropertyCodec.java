package xyz.endelith.server.codec.profile;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.util.profile.GameProfile.Property;

public final class PropertyCodec {

    private PropertyCodec() {}

    public static final StreamCodec<Property> STREAM_CODEC = StreamCodec.of(
        StreamCodec.STRING, Property::name,
        StreamCodec.STRING, Property::value,
        StreamCodec.STRING.optional(), Property::signature,
        Property::new
    );

    public static final Codec<Property> STRING_CODEC = StructCodec.of(
        "name", Codec.STRING, Property::name,
        "value", Codec.STRING, Property::value,
        "signature", Codec.STRING.optional(), Property::signature,
        Property::new
    );
}
