package xyz.endelith.event;

import java.util.Objects;

public record EventKey<O extends EventOwner, E extends Event>(
        Class<E> type,
        Object discriminator,
        boolean ordered
) {

    public EventKey {
        Objects.requireNonNull(type, "type");
    }

    public static <O extends EventOwner, E extends Event> EventKey<O, E> ordered(Class<E> type) {
        return new EventKey<>(type, null, true);
    }

    public static <O extends EventOwner, E extends Event> EventKey<O, E> unordered(Class<E> type) {
        return new EventKey<>(type, null, false);
    }

    public static <O extends EventOwner, E extends Event> EventKey<O, E> ordered(Class<E> type, Object discriminator) {
        Objects.requireNonNull(discriminator, "discriminator");
        return new EventKey<>(type, discriminator, true);
    }

    public static <O extends EventOwner, E extends Event> EventKey<O, E> unordered(Class<E> type, Object discriminator) {
        Objects.requireNonNull(discriminator, "discriminator");
        return new EventKey<>(type, discriminator, false);
    }
}
