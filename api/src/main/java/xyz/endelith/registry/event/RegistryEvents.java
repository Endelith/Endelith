package xyz.endelith.registry.event;

import xyz.endelith.chat.ChatType;
import xyz.endelith.entity.variant.cat.CatSoundVariant;
import xyz.endelith.entity.variant.cat.CatVariant;
import xyz.endelith.entity.variant.chicken.ChickenSoundVariant;
import xyz.endelith.entity.variant.chicken.ChickenVariant;
import xyz.endelith.entity.variant.cow.CowSoundVariant;
import xyz.endelith.entity.variant.cow.CowVariant;
import xyz.endelith.registry.reference.RegistryReference;
import xyz.endelith.world.biome.Biome;
import xyz.endelith.world.block.banner.BannerPattern;

public final class RegistryEvents {

    public static final RegistryEventProvider<BannerPattern> BANNER_PATTERN =
            create(RegistryReference.BANNER_PATTERN);

    public static final RegistryEventProvider<Biome> BIOME =
            create(RegistryReference.BIOME);

    public static final RegistryEventProvider<CatSoundVariant> CAT_SOUND_VARIANT =
            create(RegistryReference.CAT_SOUND_VARIANT);

    public static final RegistryEventProvider<CatVariant> CAT_VARIANT =
            create(RegistryReference.CAT_VARIANT);

    public static final RegistryEventProvider<ChatType> CHAT_TYPE =
            create(RegistryReference.CHAT_TYPE);

    public static final RegistryEventProvider<ChickenSoundVariant> CHICKEN_SOUND_VARIANT =
            create(RegistryReference.CHICKEN_SOUND_VARIANT);

    public static final RegistryEventProvider<ChickenVariant> CHICKEN_VARIANT =
            create(RegistryReference.CHICKEN_VARIANT);

    public static final RegistryEventProvider<CowSoundVariant> COW_SOUND_VARIANT =
            create(RegistryReference.COW_SOUND_VARIANT);

    public static final RegistryEventProvider<CowVariant> COW_VARIANT =
            create(RegistryReference.COW_VARIANT);

    private RegistryEvents() {
    }

    private static <V> RegistryEventProvider<V> create(RegistryReference<V> reference) {
        return new RegistryEventProvider<>(reference);
    }
}
