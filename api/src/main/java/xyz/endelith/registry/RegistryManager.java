package xyz.endelith.registry;

import java.util.Map;
import com.google.common.collect.Multimap;
import net.kyori.adventure.key.Key;
import xyz.endelith.registry.reference.RegistryReference;

public interface RegistryManager {

    <V> MinecraftRegistry<V> registry(RegistryReference<V> reference);

    void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags);
}
