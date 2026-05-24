package xyz.endelith.registry.event;

import java.util.Objects;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.event.Event;
import xyz.endelith.registry.feature.KnownPack;
import xyz.endelith.registry.reference.RegistryReference;

public record RegistryComposeEvent<V>(
        RegistryReference<V> reference,
        RegistryAccess<V> access
) implements Event {

    public RegistryComposeEvent {
        Objects.requireNonNull(reference, "reference");
        Objects.requireNonNull(access, "access");
    }

    public void register(Key key, V value, @Nullable KnownPack pack) {
        this.access.register(key, value, pack);
    }

    public interface RegistryAccess<V> {

        void register(Key key, V value, @Nullable KnownPack pack);
    }
}
