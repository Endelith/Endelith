package xyz.endelith.registry.event;

import java.util.Objects;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.event.EventKey;
import xyz.endelith.plugin.bootstrap.BootstrapContext;
import xyz.endelith.registry.reference.RegistryReference;

public final class RegistryEventProvider<V> {

    private final RegistryReference<V> reference;

    RegistryEventProvider(RegistryReference<V> reference) {
        this.reference = Objects.requireNonNull(reference, "reference");
    }

    public EventKey<BootstrapContext, RegistryComposeEvent<V>> compose() {
        return EventKey.ordered(composeEventType(), this.reference);
    }

    public EventKey<BootstrapContext, RegistryEntryAddEvent<V>> entryAdd() {
        return EventKey.ordered(entryAddEventType(), new EntryAddKey<>(this.reference, null));
    }

    public EventKey<BootstrapContext, RegistryEntryAddEvent<V>> entryAdd(Key key) {
        Objects.requireNonNull(key, "key");
        return EventKey.ordered(entryAddEventType(), new EntryAddKey<>(this.reference, key));
    }

    public RegistryReference<V> reference() {
        return this.reference;
    }

    @SuppressWarnings("unchecked")
    private static <V> Class<RegistryComposeEvent<V>> composeEventType() {
        return (Class<RegistryComposeEvent<V>>) (Class<?>) RegistryComposeEvent.class;
    }

    @SuppressWarnings("unchecked")
    private static <V> Class<RegistryEntryAddEvent<V>> entryAddEventType() {
        return (Class<RegistryEntryAddEvent<V>>) (Class<?>) RegistryEntryAddEvent.class;
    }

    public record EntryAddKey<V>(
            RegistryReference<V> reference,
            @Nullable Key entryKey
    ) {
        public EntryAddKey {
            Objects.requireNonNull(reference, "reference");
        }
    }
}
