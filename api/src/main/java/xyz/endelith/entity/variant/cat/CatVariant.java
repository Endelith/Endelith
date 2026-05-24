package xyz.endelith.entity.variant.cat;

import java.util.Objects;
import net.kyori.adventure.key.Key;

public record CatVariant(Key asset, Key babyAsset) {

    public CatVariant {
        Objects.requireNonNull(asset, "asset");
        Objects.requireNonNull(babyAsset, "baby asset");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .asset(this.asset)
                .babyAsset(this.babyAsset);
    }

    public static final class Builder {

        private Key asset;
        private Key babyAsset;

        private Builder() {
        }

        public Builder asset(Key asset) {
            this.asset = Objects.requireNonNull(asset, "asset");
            return this;
        }

        public Builder babyAsset(Key babyAsset) {
            this.babyAsset = Objects.requireNonNull(babyAsset, "baby asset");
            return this;
        }

        public CatVariant build() {
            return new CatVariant(this.asset, this.babyAsset);
        }
    }
}
