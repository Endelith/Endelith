package xyz.endelith.entity.variant.chicken;

import java.util.Objects;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

public record ChickenVariant(Key asset, Key babyAsset, ModelType modelType) {

    public ChickenVariant {
        Objects.requireNonNull(asset, "asset");
        Objects.requireNonNull(babyAsset, "baby asset");
        Objects.requireNonNull(modelType, "model type");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .asset(this.asset)
                .babyAsset(this.babyAsset)
                .modelType(this.modelType);
    }

    public static final class Builder {

        private @Nullable Key asset;
        private @Nullable Key babyAsset;
        private @Nullable ModelType modelType;

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

        public Builder modelType(ModelType modelType) {
            this.modelType = Objects.requireNonNull(modelType, "model type");
            return this;
        }

        public ChickenVariant build() {
            return new ChickenVariant(
                    Objects.requireNonNull(this.asset, "asset"),
                    Objects.requireNonNull(this.babyAsset, "baby asset"),
                    Objects.requireNonNull(this.modelType, "model type")
            );
        }
    }

    public enum ModelType {
        NORMAL,
        COLD;
    }
}
