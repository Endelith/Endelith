package xyz.endelith.server.registry;

import java.util.Set;

import org.jspecify.annotations.Nullable;

import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;

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
}
