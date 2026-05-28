package xyz.endelith.registry.event;

import xyz.endelith.chat.ChatType;
import xyz.endelith.entity.variant.cat.CatSoundVariant;
import xyz.endelith.entity.variant.cat.CatVariant;
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

    private RegistryEvents() {
    }

    private static <V> RegistryEventProvider<V> create(RegistryReference<V> reference) {
        return new RegistryEventProvider<>(reference);
    }
}
