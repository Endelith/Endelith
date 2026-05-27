package xyz.endelith.server.registry.codec.entity.variant.cat;

import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.variant.cat.CatSoundVariant;
import xyz.endelith.server.registry.codec.world.sound.SoundEventCodec;

public final class CatSoundVariantCodec {

    private static final StructCodec<CatSoundVariant.SoundProfile> SOUND_PROFILE_CODEC = StructCodec.of(
            "ambient_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::ambientSound,
            "beg_for_food_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::begForFoodSound,
            "death_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::deathSound,
            "eat_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::eatSound,
            "hiss_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::hissSound,
            "hurt_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::hurtSound,
            "purr_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::purrSound,
            "purreow_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::purreowSound,
            "stray_ambient_sound", SoundEventCodec.HOLDER_CODEC, CatSoundVariant.SoundProfile::strayAmbientSound,
            CatSoundVariant.SoundProfile::new
    );

    public static final StructCodec<CatSoundVariant> CODEC = StructCodec.of(
            "adult_sounds", SOUND_PROFILE_CODEC, CatSoundVariant::adultSounds,
            "baby_sounds", SOUND_PROFILE_CODEC, CatSoundVariant::babySounds,
            CatSoundVariant::new
    );

    private CatSoundVariantCodec() {
    }
}
