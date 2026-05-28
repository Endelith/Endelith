package xyz.endelith.server.registry;

import com.google.common.base.Functions;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.event.EventManager;
import xyz.endelith.plugin.bootstrap.BootstrapContext;
import xyz.endelith.registry.RegistryManager;
import xyz.endelith.registry.event.RegistryComposeEvent;
import xyz.endelith.registry.event.RegistryComposeEvent.RegistryAccess;
import xyz.endelith.registry.event.RegistryEntryAddEvent;
import xyz.endelith.registry.event.RegistryEventProvider;
import xyz.endelith.registry.event.RegistryEvents;
import xyz.endelith.registry.feature.KnownPack;
import xyz.endelith.registry.reference.RegistryReference;
import xyz.endelith.server.network.NetworkManager;
import xyz.endelith.server.registry.MinecraftRegistryImpl.RegistrationInfo;
import xyz.endelith.server.registry.blockstate.BlockStateRegistryImpl;
import xyz.endelith.server.registry.codec.chat.ChatTypeCodec;
import xyz.endelith.server.registry.codec.entity.variant.cat.CatSoundVariantCodec;
import xyz.endelith.server.registry.codec.entity.variant.cat.CatVariantCodec;
import xyz.endelith.server.registry.codec.world.biome.BiomeCodec;
import xyz.endelith.server.registry.codec.world.block.banner.BannerPatternCodec;
import xyz.endelith.server.registry.codec.world.sound.SoundEventCodec;
import xyz.endelith.server.util.data.DataUtil;
import xyz.endelith.server.world.block.entity.BlockEntityTypeImpl;

public final class RegistryManagerImpl implements RegistryManager {

    private final @Nullable NetworkManager networkManager;
    private final Map<RegistryReference<?>, MinecraftRegistryImpl<?>> registries;
    private final BlockStateRegistryImpl blockStateRegistry;

    private final ReadWriteLock tagsLock = new ReentrantReadWriteLock();

    public RegistryManagerImpl(EventManager<BootstrapContext> eventManager, @Nullable NetworkManager networkManager) {
        Objects.requireNonNull(eventManager, "event manager");
        this.networkManager = networkManager;

        this.registries = new RegistryMapBuilder(eventManager, this.tagsLock)
                .dataDriven(
                        RegistryEvents.BANNER_PATTERN,
                        Key.key("banner_pattern"),
                        "registries/banner_patterns.json",
                        BannerPatternCodec.CODEC
                )
                .dataDriven(
                        RegistryEvents.BIOME,
                        Key.key("biome"),
                        "registries/biomes.json",
                        BiomeCodec.CODEC
                )
                .dataDriven(
                        RegistryEvents.CAT_SOUND_VARIANT,
                        Key.key("cat_sound_variant"),
                        "registries/cat_sound_variants.json",
                        CatSoundVariantCodec.CODEC
                )
                .dataDriven(
                        RegistryEvents.CAT_VARIANT,
                        Key.key("cat_variant"),
                        "registries/cat_variants.json",
                        CatVariantCodec.CODEC
                )
                .dataDriven(
                        RegistryEvents.CHAT_TYPE,
                        Key.key("chat_type"),
                        "registries/chat_types.json",
                        ChatTypeCodec.CODEC
                )
                .builtIn(
                        RegistryReference.BLOCK,
                        Key.key("block"),
                        "registries/blocks.json",
                        BlockStateRegistryImpl.BLOCK_CODEC,
                        Functions.identity()
                )
                .builtIn(
                        RegistryReference.BLOCK_ENTITY_TYPE,
                        Key.key("block_entity_type"),
                        "registries/block_entity_types.json",
                        Codec.KEY.list(),
                        BlockEntityTypeImpl::convert
                )
                .builtIn(
                        RegistryReference.SOUND_EVENT,
                        Key.key("sound_event"),
                        "registries/sound_events.json",
                        SoundEventCodec.CODEC,
                        Functions.identity()
                )
                .build();

        this.blockStateRegistry = new BlockStateRegistryImpl();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> MinecraftRegistryImpl<V> registry(RegistryReference<V> reference) {
        Objects.requireNonNull(reference, "registry reference");

        if (!this.registries.containsKey(reference)) {
            throw new IllegalArgumentException("The specified registry reference is not recognised");
        }

        return (MinecraftRegistryImpl<V>) this.registries.get(reference);
    }

    @Override
    public BlockStateRegistryImpl blockStateRegistry() {
        return this.blockStateRegistry;
    }

    @Override
    public void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags) {
        Objects.requireNonNull(tags, "tags");

        try {
            this.tagsLock.writeLock().lock();
            tags.forEach((reference, tagToKeysMap) -> registry(reference).updateTags(tagToKeysMap));
        } finally {
            this.tagsLock.writeLock().unlock();
        }
    }

    // public void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags) {
    //     Objects.requireNonNull(tags, "tags");
    //
    //     try {
    //         this.tagsLock.writeLock().lock();
    //         tags.forEach((reference, tagToKeysMap) -> registry(reference).updateTags(tagToKeysMap));
    //
    //         ServerCommonUpdateTagsPacket updatePacket = createTagsPacket();
    //         this.networkManager.connections().forEach(connection -> {
    //             switch (connection.getState()) {
    //                 case CONFIGURATION -> connection.sendTags(updatePacket, false);
    //                 case PLAY -> connection.sendPacket(updatePacket);
    //                 default -> { /* no-op */ }
    //             }
    //         });
    //     } finally {
    //         this.tagsLock.writeLock().unlock();
    //     }
    // }

    public Collection<MinecraftRegistryImpl<?>> registries() {
        return this.registries.values();
    }

    // public void initializeTags(PlayerConnectionImpl connection) {
    //     try {
    //         this.tagsLock.readLock().lock();
    //         connection.sendTags(createTagsPacket(), true);
    //     } finally {
    //         this.tagsLock.readLock().unlock();
    //     }
    // }

    // private ServerCommonUpdateTagsPacket createTagsPacket() {
    //     List<ServerCommonUpdateTagsPacket.TagRegistry> tagRegistries = registries().stream()
    //         .map(MinecraftRegistryImpl::createTagRegistry)
    //         .toList();
    //     return new ServerCommonUpdateTagsPacket(tagRegistries);
    // }

    private static final class RegistryMapBuilder {

        private final EventManager<BootstrapContext> eventManager;
        private final ReadWriteLock tagsLock;
        private final Map<RegistryReference<?>, MinecraftRegistryImpl<?>> registries = new HashMap<>();

        private RegistryMapBuilder(EventManager<BootstrapContext> eventManager, ReadWriteLock tagsLock) {
            this.eventManager = Objects.requireNonNull(eventManager, "event manager");
            this.tagsLock = Objects.requireNonNull(tagsLock, "tags lock");
        }

        private <V> RegistryMapBuilder dataDriven(
                RegistryEventProvider<V> provider,
                Key registryKey,
                String resourcePath,
                Codec<V> valueCodec
        ) {
            Objects.requireNonNull(provider, "provider");
            return putDataDriven(provider, registryKey, resourcePath, valueCodec);
        }

        private <D, V> RegistryMapBuilder builtIn(
                RegistryReference<V> reference,
                Key registryKey,
                String resourcePath,
                Codec<D> dataCodec,
                Function<D, V> valueConverter
        ) {
            Objects.requireNonNull(reference, "reference");
            return putBuiltIn(reference, registryKey, resourcePath, dataCodec, valueConverter);
        }

        private <V> RegistryMapBuilder putDataDriven(
                RegistryEventProvider<V> provider,
                Key registryKey,
                String resourcePath,
                Codec<V> valueCodec
        ) {
            RegistryReference<V> reference = provider.reference();

            if (this.registries.containsKey(reference)) {
                throw new IllegalArgumentException(String.format(
                        "Registry with reference %s has already been registered",
                        reference
                ));
            }

            DataUtil.LoadResult<V> result = DataUtil.loadEntries(
                    resourcePath,
                    valueCodec,
                    Function.identity()
            );

            NetworkableRegistryBuilder<V> builder = new NetworkableRegistryBuilder<>(
                    provider,
                    result.registrations()
            );

            this.eventManager.fire(
                    provider.compose(),
                    new RegistryComposeEvent<>(reference, builder)
            );

            MinecraftRegistryImpl<V> registry = builder.build(
                    registryKey,
                    this.tagsLock,
                    valueCodec,
                    result.tags()
            );

            this.registries.put(reference, registry);
            return this;
        }

        private <D, V> RegistryMapBuilder putBuiltIn(
                RegistryReference<V> reference,
                Key registryKey,
                String resourcePath,
                Codec<D> dataCodec,
                Function<D, V> valueConverter
        ) {
            if (this.registries.containsKey(reference)) {
                throw new IllegalArgumentException(String.format(
                        "Registry with reference %s has already been registered",
                        reference
                ));
            }

            DataUtil.LoadResult<V> result = DataUtil.loadEntries(
                    resourcePath,
                    dataCodec,
                    valueConverter
            );

            MinecraftRegistryImpl<V> registry = new MinecraftRegistryImpl<>(
                    registryKey,
                    null,
                    List.copyOf(result.registrations()),
                    result.tags(),
                    this.tagsLock
            );

            this.registries.put(reference, registry);
            return this;
        }

        public Map<RegistryReference<?>, MinecraftRegistryImpl<?>> build() {
            return Map.copyOf(this.registries);
        }

        private final class NetworkableRegistryBuilder<V> implements RegistryAccess<V> {

            private final RegistryEventProvider<V> provider;
            private final Map<Key, EntryBuilder<V>> registrations = new LinkedHashMap<>();
            private boolean registryCreated;

            private NetworkableRegistryBuilder(
                    RegistryEventProvider<V> provider,
                    List<RegistrationInfo<V>> initialRegistrations
            ) {
                this.provider = Objects.requireNonNull(provider, "provider");
                Objects.requireNonNull(initialRegistrations, "initial registrations");

                for (RegistrationInfo<V> registration : initialRegistrations) {
                    this.registrations.put(
                            registration.key(),
                            new EntryBuilder<>(
                                    registration.key(),
                                    registration.value(),
                                    registration.pack()
                            )
                    );
                }
            }

            @Override
            public void register(Key key, V value, @Nullable KnownPack pack) {
                Objects.requireNonNull(key, "key");
                Objects.requireNonNull(value, "value");

                if (this.registryCreated) {
                    throw new IllegalStateException("The registry has already been created");
                }

                if (this.registrations.containsKey(key)) {
                    throw new IllegalArgumentException(String.format(
                            "Registry entry with key \"%s\" has already been registered",
                            key
                    ));
                }

                this.registrations.put(key, new EntryBuilder<>(key, value, pack));
            }

            private MinecraftRegistryImpl<V> build(
                    Key registryKey,
                    ReadWriteLock tagsLock,
                    Codec<V> valueCodec,
                    Multimap<Key, Key> tags
            ) {
                this.registryCreated = true;

                for (EntryBuilder<V> registration : this.registrations.values()) {
                    RegistryEntryAddEvent<V> event = new RegistryEntryAddEvent<>(
                            this.provider.reference(),
                            registration.key(),
                            registration.value(),
                            registration.pack()
                    );

                    RegistryMapBuilder.this.eventManager.fire(this.provider.entryAdd(), event);
                    RegistryMapBuilder.this.eventManager.fire(this.provider.entryAdd(registration.key()), event);

                    registration.setValue(event.value());
                    registration.setPack(event.pack());
                }

                List<RegistrationInfo<V>> finalRegistrations = this.registrations.values()
                        .stream()
                        .map(registration -> new RegistrationInfo<>(
                                registration.key(),
                                registration.value(),
                                registration.pack()
                        ))
                        .toList();

                return new MinecraftRegistryImpl<>(
                        registryKey,
                        valueCodec,
                        finalRegistrations,
                        tags,
                        tagsLock
                );
            }
        }

        private static final class EntryBuilder<V> {

            private final Key key;
            private V value;
            private @Nullable KnownPack pack;

            private EntryBuilder(Key key, V value, @Nullable KnownPack pack) {
                this.key = Objects.requireNonNull(key, "key");
                this.value = Objects.requireNonNull(value, "value");
                this.pack = pack;
            }

            private Key key() {
                return this.key;
            }

            private V value() {
                return this.value;
            }

            private void setValue(V value) {
                this.value = Objects.requireNonNull(value, "value");
            }

            private @Nullable KnownPack pack() {
                return this.pack;
            }

            private void setPack(@Nullable KnownPack pack) {
                this.pack = pack;
            }
        }
    }
}
