package xyz.endelith.registry;

import java.util.Set;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

public interface MinecraftRegistry<V> {

    @Nullable V get(Key key);

    Set<Key> keySet();

    Set<Key> tagsFor(Key key);
}
