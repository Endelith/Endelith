package xyz.endelith.registry;

import java.util.Map;
import java.util.Set;

import net.kyori.adventure.key.Key;
import xyz.endelith.registry.reference.RegistryReference;

public interface RegistryManager {
    <V> MinecraftRegistry<V> registry(RegistryReference<V> reference);
    void updateTags(Map<RegistryReference<?>, Map<Key, Set<Key>>> tags);
}
