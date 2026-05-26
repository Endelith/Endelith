package xyz.endelith.registry.event;

import java.util.Objects;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.event.Event;
import xyz.endelith.registry.feature.KnownPack;
import xyz.endelith.registry.reference.RegistryReference;

public final class RegistryEntryAddEvent<V> implements Event {

    private final RegistryReference<V> reference;
    private final Key key;

    private V value;
    private @Nullable KnownPack pack;

    public RegistryEntryAddEvent(
            RegistryReference<V> reference,
            Key key,
            V value,
            @Nullable KnownPack pack
    ) {
        this.reference = Objects.requireNonNull(reference, "reference");
        this.key = Objects.requireNonNull(key, "key");
        this.value = Objects.requireNonNull(value, "value");
        this.pack = pack;
    }

    public RegistryReference<V> reference() {
        return this.reference;
    }

    public Key key() {
        return this.key;
    }

    public V value() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    public @Nullable KnownPack pack() {
        return this.pack;
    }

    public void setPack(@Nullable KnownPack pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return "RegistryEntryAddEvent["
                + "reference=" + this.reference
                + ", key=" + this.key
                + ", value=" + this.value
                + ", pack=" + this.pack
                + "]";
    }
}
