package xyz.endelith.server.registry.codec.world.sound;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.world.sound.SoundEvent;

public final class SoundEventCodec {

    public static final StructCodec<SoundEvent> CODEC = StructCodec.of(
        "sound", Codec.KEY, SoundEvent::sound,
        SoundEvent::new
    );

    private SoundEventCodec() {
    }
}
