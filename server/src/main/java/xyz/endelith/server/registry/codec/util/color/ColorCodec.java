package xyz.endelith.server.registry.codec.util.color;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.util.color.Color;

public final class ColorCodec {

    public static final Codec<Color> CODEC = Codec.INT.transform(Color::new, Color::value);

    private ColorCodec() {
    }
}
