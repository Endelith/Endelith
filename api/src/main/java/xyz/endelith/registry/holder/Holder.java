package xyz.endelith.registry.holder;

import java.util.Objects;

import org.jspecify.annotations.Nullable;

import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;

public interface Holder<V> {
    @Nullable V value(MinecraftRegistry<? extends V> registry);

    record Direct<V>(V value) implements Holder<V> {

        public Direct {
            Objects.requireNonNull(value, "value");
        }

        @Override
        public @Nullable V value(MinecraftRegistry<? extends V> registry) {
            return value;
        }
    }

    record Reference<V>(Key key) implements Holder<V> {

        public Reference {
            Objects.requireNonNull(key, "key");
        }

        @Override
        public @Nullable V value(MinecraftRegistry<? extends V> registry) {
            return registry.get(key);
        } 
    }
}
