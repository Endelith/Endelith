package xyz.endelith.server.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.registry.MinecraftRegistry;
import xyz.endelith.registry.feature.KnownPack;

public final class MinecraftRegistryImpl<V> implements MinecraftRegistry<V> {

    private final Key registryKey;
    private final @Nullable Codec<V> valueCodec;
    private final List<RegistrationInfo<V>> registrationInfos;
    private final ReadWriteLock tagsLock;
    private final Multimap<Key, Key> tagToKeys = HashMultimap.create();
    private final Multimap<Key, Key> keyToTags = HashMultimap.create();

    public MinecraftRegistryImpl(
            Key registryKey,
            @Nullable Codec<V> valueCodec,
            List<RegistrationInfo<V>> registrationInfos,
            Multimap<Key, Key> tags,
            ReadWriteLock tagsLock
    ) {
        this.registryKey = Objects.requireNonNull(registryKey, "registry key");
        this.valueCodec = valueCodec;
        Objects.requireNonNull(registrationInfos, "registration infos");
        this.registrationInfos = List.copyOf(registrationInfos);
        this.tagsLock = Objects.requireNonNull(tagsLock, "tags lock");
        updateTags(Objects.requireNonNull(tags, "tags"));
    }

    @Override
    public @Nullable V get(Key key) {
        Objects.requireNonNull(key, "key");
        for (RegistrationInfo<V> info : this.registrationInfos) {
            if (info.key().equals(key)) {
                return info.value();
            }
        }
        return null;
    }

    @Override
    public Set<Key> keySet() {
        Set<Key> keys = new HashSet<>();
        for (RegistrationInfo<V> info : this.registrationInfos) {
            keys.add(info.key());
        }
        return keys;
    }

    @Override
    public Set<Key> tagsFor(Key key) {
        try {
            this.tagsLock.readLock().lock();
            return Set.copyOf(this.keyToTags.get(ensureRegistered(key)));
        } finally {
            this.tagsLock.readLock().unlock();
        }
    }

    void updateTags(Multimap<Key, Key> newTagMap) {
        this.tagToKeys.clear();
        this.keyToTags.clear();
        newTagMap.forEach((tag, key) -> {
            ensureRegistered(key);
            this.tagToKeys.put(tag, key);
            this.keyToTags.put(key, tag);
        });
    }

    public @Nullable Codec<V> valueCodec() {
        return this.valueCodec;
    }

    public List<RegistrationInfo<V>> registrationInfos() {
        return this.registrationInfos;
    }

    public Key registryKey() {
        return this.registryKey;
    }

    private Key ensureRegistered(Key key) {
        Objects.requireNonNull(key, "key");
        for (RegistrationInfo<V> info : this.registrationInfos) {
            if (info.key().equals(key)) {
                return key;
            }
        }
        throw new IllegalArgumentException("This registry does not contain a value for \"" + key + "\" key");
    }

    public record RegistrationInfo<V>(Key key, V value, @Nullable KnownPack pack) {
        public RegistrationInfo {
            Objects.requireNonNull(key, "key");
            Objects.requireNonNull(value, "value");
        }
    }
}
