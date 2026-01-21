package xyz.endelith.registry;

import java.util.Set;

import org.jspecify.annotations.Nullable;

import net.kyori.adventure.key.Key;

public interface MinecraftRegistry<V> {
    @Nullable V get(Key key);
    Set<Key> keySet();
    Set<Key> tagsFor(Key key);
}
