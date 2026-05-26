package xyz.endelith.registry.reference;

import java.util.Objects;
import xyz.endelith.entity.variant.cat.CatVariant;
import xyz.endelith.world.block.BlockType;
import xyz.endelith.world.block.banner.BannerPattern;
import xyz.endelith.world.sound.SoundEvent;

public final class RegistryReference<V> {

    public static final RegistryReference<BannerPattern> BANNER_PATTERN = create("banner_pattern");

    public static final RegistryReference<BlockType> BLOCK = create("block");

    public static final RegistryReference<CatVariant> CAT_VARIANT = create("cat_variant");

    public static final RegistryReference<SoundEvent> SOUND_EVENT = create("sound_event");

    private final String name;

    private RegistryReference(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public String name() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("RegistryReference{name='%s'}", this.name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof RegistryReference<?> reference)) {
            return false;
        }

        return this.name.equals(reference.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    private static <V> RegistryReference<V> create(String name) {
        return new RegistryReference<>(name);
    }
}
