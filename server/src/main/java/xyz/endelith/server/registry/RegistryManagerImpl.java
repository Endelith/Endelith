package xyz.endelith.server.registry;

import com.google.common.collect.Multimap;
import java.util.Map;
import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;
import xyz.endelith.registry.RegistryManager;
import xyz.endelith.registry.reference.RegistryReference;

public final class RegistryManagerImpl implements RegistryManager {

    @Override
    public <V> MinecraftRegistry<V> registry(RegistryReference<V> reference) {
        throw new UnsupportedOperationException("Unimplemented method 'registry'");
    }

    @Override
    public void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags) {
        throw new UnsupportedOperationException("Unimplemented method 'updateTags'");
    }
}
