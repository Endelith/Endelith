package xyz.endelith.entity.variant.cat;

import java.util.Objects;
import net.kyori.adventure.key.Key;

public record CatVariant(Key asset, Key babyAsset) {
    public CatVariant {
        Objects.requireNonNull(asset, "asset");
        Objects.requireNonNull(babyAsset, "baby asset");
    }
}
