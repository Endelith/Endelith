package xyz.endelith.server.registry;

import java.util.Map;
import java.util.Set;

import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;
import xyz.endelith.registry.RegistryManager;
import xyz.endelith.registry.reference.RegistryReference;

public final class RegistryManagerImpl implements RegistryManager {
    
    private final Map<RegistryReference<?>, MinecraftRegistryImpl<?>> registries;

    @Override
    public <V> MinecraftRegistry<V> registry(RegistryReference<V> reference) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registry'");
    }

    @Override
    public void updateTags(Map<RegistryReference<?>, Map<Key, Set<Key>>> tags) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTags'");
    }
}
