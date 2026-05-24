package xyz.endelith.server.registry;

import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import xyz.endelith.registry.event.RegistryEvents;
import xyz.endelith.registry.event.RegistryInitializeEvent;
import xyz.endelith.registry.event.RegistryInitializeEvent.RegistryAccess;
import xyz.endelith.registry.feature.KnownPack;
import xyz.endelith.registry.reference.RegistryReference;
import xyz.endelith.server.network.NetworkManager;
import xyz.endelith.server.registry.MinecraftRegistryImpl.RegistrationInfo;
import xyz.endelith.server.registry.codec.entity.variant.cat.CatVariantCodec;
import xyz.endelith.server.registry.codec.world.block.banner.BannerPatternCodec;
import xyz.endelith.server.util.data.DataUtil;

public final class RegistryManagerImpl implements RegistryManager {

    private final NetworkManager networkManager;
    private final Map<RegistryReference<?>, MinecraftRegistryImpl<?>> registries;
    private final ReadWriteLock tagsLock = new ReentrantReadWriteLock();

    public RegistryManagerImpl(EventManager<BootstrapContext> eventManager, NetworkManager networkManager) {
        Objects.requireNonNull(eventManager, "event manager");
        // this.networkManager = Objects.requireNonNull(networkManager, "network manager");
        this.networkManager = networkManager;

        this.registries = new RegistryMapBuilder(eventManager, this.tagsLock)
            .dataDriven(
                    RegistryReference.CAT_VARIANT,
                    Key.key("cat_variant"),
                    "registries/cat_variants.json",
                    CatVariantCodec.CODEC
            )
            .dataDriven(
                    RegistryReference.BANNER_PATTERN,
                    Key.key("banner_pattern"),
                    "registries/banner_patterns.json",
                    BannerPatternCodec.CODEC
            )
            .build();
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
    public void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags) {
        Objects.requireNonNull(tags, "tags");

        try {
            this.tagsLock.writeLock().lock();
            tags.forEach((reference, tagToKeysMap) -> registry(reference).updateTags(tagToKeysMap));
        } finally {
            this.tagsLock.writeLock().unlock();
        }
    }

    //public void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags) {
    //    Objects.requireNonNull(tags, "tags");

    //    try {
    //        this.tagsLock.writeLock().lock();
    //        tags.forEach((reference, tagToKeysMap) -> registry(reference).updateTags(tagToKeysMap));

    //        ServerCommonUpdateTagsPacket updatePacket = createTagsPacket();
    //        this.networkManager.connections().forEach(connection -> {
    //            switch (connection.getState()) {
    //                case CONFIGURATION -> connection.sendTags(updatePacket, false);
    //                case PLAY -> connection.sendPacket(updatePacket);
    //                default -> { /* no-op */ }
    //            }
    //        });
    //    } finally {
    //        this.tagsLock.writeLock().unlock();
    //    }
    //}

    public Collection<MinecraftRegistryImpl<?>> registries() {
        return this.registries.values();
    }

    //public void initializeTags(PlayerConnectionImpl connection) {
    //    try {
    //        this.tagsLock.readLock().lock();
    //        connection.sendTags(createTagsPacket(), true);
    //    } finally {
    //        this.tagsLock.readLock().unlock();
    //    }
    //}

    //private ServerCommonUpdateTagsPacket createTagsPacket() {
    //    List<ServerCommonUpdateTagsPacket.TagRegistry> tagRegistries = registries().stream()
    //        .map(MinecraftRegistryImpl::createTagRegistry)
    //        .toList();
    //    return new ServerCommonUpdateTagsPacket(tagRegistries);
    //}

    private static final class RegistryMapBuilder {

        private final EventManager<BootstrapContext> eventManager;
        private final ReadWriteLock tagsLock;
        private final Map<RegistryReference<?>, MinecraftRegistryImpl<?>> registries = new HashMap<>();

        private RegistryMapBuilder(EventManager<BootstrapContext> eventManager, ReadWriteLock tagsLock) {
            this.eventManager = Objects.requireNonNull(eventManager, "event manager");
            this.tagsLock = Objects.requireNonNull(tagsLock, "tags lock");
        }

        private <V> RegistryMapBuilder dataDriven(
                RegistryReference<V> reference,
                Key registryKey,
                String resourcePath,
                Codec<V> valueCodec
        ) {
            return put(reference, registryKey, resourcePath, valueCodec, Function.identity(), valueCodec);
        }

        private <D, V> RegistryMapBuilder builtIn(
                RegistryReference<V> reference,
                Key registryKey,
                String resourcePath,
                Codec<D> dataCodec,
                Function<D, V> valueConverter
        ) {
            return put(reference, registryKey, resourcePath, dataCodec, valueConverter, null);
        }

        private <D, V> RegistryMapBuilder put(
                RegistryReference<V> reference,
                Key registryKey,
                String resourcePath,
                Codec<D> dataCodec,
                Function<D, V> valueConverter,
                @Nullable Codec<V> valueCodec
        ) {
            if (this.registries.containsKey(reference)) {
                throw new IllegalArgumentException(String.format(
                    "Registry with reference %s has already been registered", reference
                ));
            }

            DataUtil.LoadResult<V> result = DataUtil.loadEntries(resourcePath, dataCodec, valueConverter);

            MinecraftRegistryImpl<V> registry;
            if (valueCodec == null) {
                registry = new MinecraftRegistryImpl<>(
                        registryKey,
                        null,
                        List.copyOf(result.registrations()),
                        result.tags(),
                        this.tagsLock
                );
            } else {
                NetworkableRegistryBuilder<V> builder = new NetworkableRegistryBuilder<>(result.registrations());
                this.eventManager.fire(
                        RegistryEvents.initialize(reference),
                        new RegistryInitializeEvent<>(reference, builder)
                );
                registry = builder.build(registryKey, this.tagsLock, valueCodec, result.tags());
            }

            this.registries.put(reference, registry);
            return this;
        }

        public Map<RegistryReference<?>, MinecraftRegistryImpl<?>> build() {
            return Map.copyOf(this.registries);
        }

        private static final class NetworkableRegistryBuilder<V> implements RegistryAccess<V> {

            private final List<RegistrationInfo<V>> registrations;
            private boolean registryCreated;

            private NetworkableRegistryBuilder(List<RegistrationInfo<V>> initialRegistrations) {
                this.registrations = new ArrayList<>(initialRegistrations);
            }

            @Override
            public void register(Key key, V value, @Nullable KnownPack pack) {
                Objects.requireNonNull(key, "key");
                Objects.requireNonNull(value, "value");

                if (this.registryCreated) {
                    throw new IllegalStateException("The registry has already been created");
                }

                this.registrations.add(new RegistrationInfo<>(key, value, pack));
            }

            private MinecraftRegistryImpl<V> build(
                    Key registryKey,
                    ReadWriteLock tagsLock,
                    Codec<V> valueCodec,
                    Multimap<Key, Key> tags
            ) {
                this.registryCreated = true;
                return new MinecraftRegistryImpl<>(
                        registryKey,
                        valueCodec,
                        List.copyOf(this.registrations),
                        tags,
                        tagsLock
                );
            }
        }
    }
}
