package xyz.endelith.world.block.banner;

import java.util.Objects;
import net.kyori.adventure.key.Key;

public record BannerPattern(Key asset, String translationKey) {
    public BannerPattern {
        Objects.requireNonNull(asset, "asset");
        Objects.requireNonNull(translationKey, "translation key");
    }
}
