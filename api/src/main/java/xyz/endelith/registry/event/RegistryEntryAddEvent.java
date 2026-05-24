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
        Entry<V> entry
) implements Event {

    public RegistryEntryAddEvent {
        Objects.requireNonNull(reference, "reference");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(entry, "entry");
    }

    public interface Entry<V> {

        Key key();

        V value();

        @Nullable KnownPack pack();

        void setValue(V value);

        void setPack(@Nullable KnownPack pack);
    }
}
