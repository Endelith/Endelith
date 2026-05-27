package xyz.endelith.world.biome;

import java.util.Objects;
import net.kyori.adventure.nbt.BinaryTag;
import org.jetbrains.annotations.Nullable;

public record Biome(
        boolean hasPrecipitation,
        float temperature,
        TemperatureModifier temperatureModifier,
        float downfall,
        @Nullable BinaryTag attributes,
        BiomeEffects effects
) {

    public Biome {
        Objects.requireNonNull(temperatureModifier, "temperature modifier");
        Objects.requireNonNull(effects, "effects");
    }

    public enum TemperatureModifier {
        NONE,
        FROZEN;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .hasPrecipitation(this.hasPrecipitation)
                .temperature(this.temperature)
                .temperatureModifier(this.temperatureModifier)
                .downfall(this.downfall)
                .attributes(this.attributes)
                .effects(this.effects);
    }

    public static final class Builder {

        private boolean hasPrecipitation;
        private float temperature = 0.5F;
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        private float downfall = 0.5F;
        private @Nullable BinaryTag attributes;
        private @Nullable BiomeEffects effects;

        private Builder() {
        }

        public Builder hasPrecipitation(boolean hasPrecipitation) {
            this.hasPrecipitation = hasPrecipitation;
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder temperatureModifier(TemperatureModifier temperatureModifier) {
            this.temperatureModifier = Objects.requireNonNull(temperatureModifier, "temperature modifier");
            return this;
        }

        public Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        public Builder attributes(@Nullable BinaryTag attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder effects(BiomeEffects effects) {
            this.effects = Objects.requireNonNull(effects, "effects");
            return this;
        }

        public Biome build() {
            return new Biome(
                    this.hasPrecipitation,
                    this.temperature,
                    this.temperatureModifier,
                    this.downfall,
                    this.attributes,
                    Objects.requireNonNull(this.effects, "effects")
            );
        }
    }
}
