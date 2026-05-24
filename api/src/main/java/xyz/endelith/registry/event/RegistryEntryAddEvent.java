package xyz.endelith.registry.event;

import java.util.Objects;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.event.Event;
import xyz.endelith.registry.feature.KnownPack;
import xyz.endelith.registry.reference.RegistryReference;

public record RegistryEntryAddEvent<V>(
        RegistryReference<V> reference,
        Key key,
        Builder<V> builder
) implements Event {

    public RegistryEntryAddEvent {
        Objects.requireNonNull(reference, "reference");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(builder, "builder");
    }

    public interface Builder<V> {

        Key key();

        V value();

        @Nullable KnownPack pack();

        void value(V value);

        void pack(@Nullable KnownPack pack);
    }
}
