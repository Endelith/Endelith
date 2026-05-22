package xyz.endelith.registry.event;

import java.util.Objects;
import xyz.endelith.event.EventKey;
import xyz.endelith.plugin.bootstrap.BootstrapContext;
import xyz.endelith.registry.reference.RegistryReference;

public final class RegistryEvents {

    private RegistryEvents() {
    }

    public static <V> EventKey<BootstrapContext, RegistryInitializeEvent<V>> initialize(
            RegistryReference<V> reference
    ) {
        Objects.requireNonNull(reference, "reference");
        return EventKey.ordered(initializeEventType(), reference);
    }

    @SuppressWarnings("unchecked")
    private static <V> Class<RegistryInitializeEvent<V>> initializeEventType() {
        return (Class<RegistryInitializeEvent<V>>) (Class<?>) RegistryInitializeEvent.class;
    }
}
