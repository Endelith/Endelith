package xyz.endelith.server.registry.codec.entity.variant.chicken;

import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.variant.chicken.ChickenSoundVariant;
import xyz.endelith.server.registry.codec.world.sound.SoundEventCodec;

public final class ChickenSoundVariantCodec {

    private static final StructCodec<ChickenSoundVariant.SoundProfile> SOUND_PROFILE_CODEC = StructCodec.of(
            "ambient_sound", SoundEventCodec.HOLDER_CODEC, ChickenSoundVariant.SoundProfile::ambientSound,
            "death_sound", SoundEventCodec.HOLDER_CODEC, ChickenSoundVariant.SoundProfile::deathSound,
            "hurt_sound", SoundEventCodec.HOLDER_CODEC, ChickenSoundVariant.SoundProfile::hurtSound,
            "step_sound", SoundEventCodec.HOLDER_CODEC, ChickenSoundVariant.SoundProfile::stepSound,
            ChickenSoundVariant.SoundProfile::new
    );

    public static final StructCodec<ChickenSoundVariant> CODEC = StructCodec.of(
            "adult_sounds", SOUND_PROFILE_CODEC, ChickenSoundVariant::adultSounds,
            "baby_sounds", SOUND_PROFILE_CODEC, ChickenSoundVariant::babySounds,
            ChickenSoundVariant::new
    );

    private ChickenSoundVariantCodec() {
    }
}
