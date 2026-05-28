package xyz.endelith.server.registry.codec.entity.variant.cow;

import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.variant.cow.CowSoundVariant;
import xyz.endelith.server.registry.codec.world.sound.SoundEventCodec;

public final class CowSoundVariantCodec {

    public static final StructCodec<CowSoundVariant> CODEC = StructCodec.of(
            "ambient_sound", SoundEventCodec.HOLDER_CODEC, CowSoundVariant::ambientSound,
            "death_sound", SoundEventCodec.HOLDER_CODEC, CowSoundVariant::deathSound,
            "hurt_sound", SoundEventCodec.HOLDER_CODEC, CowSoundVariant::hurtSound,
            "step_sound", SoundEventCodec.HOLDER_CODEC, CowSoundVariant::stepSound,
            CowSoundVariant::new
    );

    private CowSoundVariantCodec() {
    }
}
