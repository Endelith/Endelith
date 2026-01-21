package xyz.endelith.server.codec.key;

import net.kyori.adventure.key.Key;
import xyz.endelith.cosine.stream.StreamCodec;

public final class KeyCodec {

    private KeyCodec() {}

    public static final StreamCodec<Key> STREAM_CODEC =
        StreamCodec.STRING.transform(Key::asString, Key::key);
}
