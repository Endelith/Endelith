package xyz.endelith.server.registry;

import java.util.Objects;
import java.util.Set;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.registry.MinecraftRegistry;
import xyz.endelith.registry.feature.KnownPack;

public final class MinecraftRegistryImpl<V> implements MinecraftRegistry<V> {

    @Override
    public @Nullable V get(Key key) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Set<Key> keySet() {
        throw new UnsupportedOperationException("Unimplemented method 'keySet'");
    }

    @Override
    public Set<Key> tagsFor(Key key) {
        throw new UnsupportedOperationException("Unimplemented method 'tagsFor'");
    }

    public record RegistrationInfo<V>(Key key, V value, @Nullable KnownPack pack) {
        public RegistrationInfo {
            Objects.requireNonNull(key, "key");
            Objects.requireNonNull(value, "value");
        }
    }
}
