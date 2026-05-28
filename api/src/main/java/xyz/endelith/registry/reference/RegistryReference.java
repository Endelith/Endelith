package xyz.endelith.registry.reference;

import java.util.Objects;
import xyz.endelith.chat.ChatType;
import xyz.endelith.entity.damage.DamageType;
import xyz.endelith.entity.variant.cat.CatSoundVariant;
import xyz.endelith.entity.variant.cat.CatVariant;
import xyz.endelith.entity.variant.chicken.ChickenSoundVariant;
import xyz.endelith.entity.variant.chicken.ChickenVariant;
import xyz.endelith.entity.variant.cow.CowSoundVariant;
import xyz.endelith.entity.variant.cow.CowVariant;
import xyz.endelith.world.biome.Biome;
import xyz.endelith.world.block.BlockType;
import xyz.endelith.world.block.banner.BannerPattern;
import xyz.endelith.world.block.entity.BlockEntityType;
import xyz.endelith.world.sound.SoundEvent;

public final class RegistryReference<V> {

    public static final RegistryReference<BannerPattern> BANNER_PATTERN = create("banner_pattern");

    public static final RegistryReference<Biome> BIOME = create("biome");

    public static final RegistryReference<BlockEntityType> BLOCK_ENTITY_TYPE = create("block_entity_type");

    public static final RegistryReference<BlockType> BLOCK = create("block");

    public static final RegistryReference<CatSoundVariant> CAT_SOUND_VARIANT = create("cat_sound_variant");

    public static final RegistryReference<CatVariant> CAT_VARIANT = create("cat_variant");

    public static final RegistryReference<ChatType> CHAT_TYPE = create("chat_type");

    public static final RegistryReference<ChickenSoundVariant> CHICKEN_SOUND_VARIANT = create("chicken_sound_varinat");

    public static final RegistryReference<ChickenVariant> CHICKEN_VARIANT = create("chicken_variant");

    public static final RegistryReference<CowSoundVariant> COW_SOUND_VARIANT = create("cow_sound_variant");

    public static final RegistryReference<CowVariant> COW_VARIANT = create("cow_variant");

    public static final RegistryReference<DamageType> DAMAGE_TYPE = create("damage_type");

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
