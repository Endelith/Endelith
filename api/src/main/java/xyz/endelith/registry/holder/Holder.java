package xyz.endelith.registry.holder;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;

public interface Holder<V> {

    @Nullable V value(MinecraftRegistry<? extends V> registry);

    V valueOrThrow(MinecraftRegistry<? extends V> registry);

    record Direct<V>(V value) implements Holder<V> {
        public Direct {
            Objects.requireNonNull(value, "value");
        }

        @Override
        public V value(MinecraftRegistry<? extends V> registry) {
            return this.value;
        }

        @Override
        public V valueOrThrow(MinecraftRegistry<? extends V> registry) {
            return this.value;
        }
    }

    record Reference<V>(Key key) implements Holder<V> {
        public Reference {
            Objects.requireNonNull(key, "key");
        }

        @Override
        public V value(MinecraftRegistry<? extends V> registry) {
            return registry.get(this.key);
        }

        @Override
        public V valueOrThrow(MinecraftRegistry<? extends V> registry) {
            V value = this.value(registry);
            if (value == null) {
                throw new IllegalArgumentException(String.format(
                        "The specified registry does not provide a value for \"%s\" key",
                        this.key
                ));
            }
            return value;
        }
    }
}
