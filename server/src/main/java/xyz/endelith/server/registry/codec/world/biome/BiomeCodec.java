package xyz.endelith.server.registry.codec.world.biome;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.server.registry.codec.util.color.ColorCodec;
import xyz.endelith.world.biome.Biome;
import xyz.endelith.world.biome.BiomeEffects;

public final class BiomeCodec {

    private static final Codec<Biome.TemperatureModifier> TEMPERATURE_MODIFIER_CODEC =
            Codec.enumOf(Biome.TemperatureModifier.class)
                    .defaultValue(Biome.TemperatureModifier.NONE);

    private static final Codec<BiomeEffects.GrassColorModifier> GRASS_COLOR_MODIFIER_CODEC =
            Codec.enumOf(BiomeEffects.GrassColorModifier.class)
                    .defaultValue(BiomeEffects.GrassColorModifier.NONE);

    private static final StructCodec<BiomeEffects> EFFECTS_CODEC = StructCodec.of(
            "water_color", ColorCodec.CODEC, BiomeEffects::waterColor,
            "foliage_color", ColorCodec.CODEC.optional(), BiomeEffects::foliageColor,
            "dry_foliage_color", ColorCodec.CODEC.optional(), BiomeEffects::dryFoliageColor,
            "grass_color", ColorCodec.CODEC.optional(), BiomeEffects::grassColor,
            "grass_color_modifier", GRASS_COLOR_MODIFIER_CODEC, BiomeEffects::grassColorModifier,
            BiomeEffects::new
    );

    public static final StructCodec<Biome> CODEC = StructCodec.of(
            "has_precipitation", Codec.BOOLEAN, Biome::hasPrecipitation,
            "temperature", Codec.FLOAT, Biome::temperature,
            "temperature_modifier", TEMPERATURE_MODIFIER_CODEC, Biome::temperatureModifier,
            "downfall", Codec.FLOAT, Biome::downfall,
            "attributes", Codec.NBT.optional(), Biome::attributes,
            "effects", EFFECTS_CODEC, Biome::effects,
            Biome::new
    );

    private BiomeCodec() {
    }
}
