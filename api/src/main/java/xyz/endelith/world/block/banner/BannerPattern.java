package xyz.endelith.world.block.banner;

import java.util.Objects;
import net.kyori.adventure.key.Key;

public record BannerPattern(Key asset, String translationKey) {

    public BannerPattern {
        Objects.requireNonNull(asset, "asset");
        Objects.requireNonNull(translationKey, "translation key");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .asset(this.asset)
                .translationKey(this.translationKey);
    }

    public static final class Builder {

        private Key asset;
        private String translationKey;

        private Builder() {
        }

        public Builder asset(Key asset) {
            this.asset = Objects.requireNonNull(asset, "asset");
            return this;
        }

        public Builder translationKey(String translationKey) {
            this.translationKey = Objects.requireNonNull(translationKey, "translation key");
            return this;
        }

        public BannerPattern build() {
            return new BannerPattern(this.asset, this.translationKey);
        }
    }
}
