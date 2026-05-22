package xyz.endelith.server.registry;

import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;
import xyz.endelith.registry.feature.KnownPack;

public final class MinecraftRegistryImpl<V> implements MinecraftRegistry<V> {

    @Override
    public @Nullable V get(Key key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Set<Key> keySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keySet'");
    }

    @Override
    public Set<Key> tagsFor(Key key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tagsFor'");
    }

    public record RegistrationInfo<V>(Key key, V value, @Nullable KnownPack pack) {
        public RegistrationInfo {
            Objects.requireNonNull(key, "key");
            Objects.requireNonNull(value, "value");
        }
    }
}
