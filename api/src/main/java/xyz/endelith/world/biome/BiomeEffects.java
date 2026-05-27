package xyz.endelith.world.biome;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.util.color.Color;

public record BiomeEffects(
        Color waterColor,
        @Nullable Color foliageColor,
        @Nullable Color dryFoliageColor,
        @Nullable Color grassColor,
        GrassColorModifier grassColorModifier
) {

    public BiomeEffects {
        Objects.requireNonNull(waterColor, "water color");
        Objects.requireNonNull(grassColorModifier, "grass color modifier");
    }

    public enum GrassColorModifier {
        NONE,
        DARK_FOREST,
        SWAMP;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .waterColor(this.waterColor)
                .foliageColor(this.foliageColor)
                .dryFoliageColor(this.dryFoliageColor)
                .grassColor(this.grassColor)
                .grassColorModifier(this.grassColorModifier);
    }

    public static final class Builder {

        private Color waterColor = new Color(0x3F76E4);
        private @Nullable Color foliageColor;
        private @Nullable Color dryFoliageColor;
        private @Nullable Color grassColor;
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;

        private Builder() {
        }

        public Builder waterColor(Color waterColor) {
            this.waterColor = Objects.requireNonNull(waterColor, "water color");
            return this;
        }

        public Builder foliageColor(@Nullable Color foliageColor) {
            this.foliageColor = foliageColor;
            return this;
        }

        public Builder dryFoliageColor(@Nullable Color dryFoliageColor) {
            this.dryFoliageColor = dryFoliageColor;
            return this;
        }

        public Builder grassColor(@Nullable Color grassColor) {
            this.grassColor = grassColor;
            return this;
        }

        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            this.grassColorModifier = Objects.requireNonNull(grassColorModifier, "grass color modifier");
            return this;
        }

        public BiomeEffects build() {
            return new BiomeEffects(
                    this.waterColor,
                    this.foliageColor,
                    this.dryFoliageColor,
                    this.grassColor,
                    this.grassColorModifier
            );
        }
    }
}
