package xyz.endelith.registry;

import com.google.common.collect.Multimap;
import java.util.Map;
import net.kyori.adventure.key.Key;
import xyz.endelith.registry.blockstate.BlockStateRegistry;
import xyz.endelith.registry.reference.RegistryReference;

public interface RegistryManager {

    <V> MinecraftRegistry<V> registry(RegistryReference<V> reference);

    BlockStateRegistry blockStateRegistry();

    void updateTags(Map<RegistryReference<?>, Multimap<Key, Key>> tags);
}
