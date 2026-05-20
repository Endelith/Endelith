package xyz.endelith.server.event;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import xyz.endelith.event.Event;
import xyz.endelith.event.EventHandler;
import xyz.endelith.event.EventKey;
import xyz.endelith.event.EventManager;
import xyz.endelith.event.EventOrder;
import xyz.endelith.event.EventOwner;

public final class EventManagerImpl<O extends EventOwner> implements EventManager<O> {

    private final ConcurrentMap<EventKey<?, ?>, CopyOnWriteArrayList<RegisteredHandler<?>>> handlers =
            new ConcurrentHashMap<>();

    @Override
    public <E extends Event> void listen(EventKey<O, E> key, EventHandler<E> handler) {
        listen(key, EventOrder.NORMAL, handler);
    }

    @Override
    public <E extends Event> void listen(EventKey<O, E> key, EventOrder order, EventHandler<E> handler) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(order, "order");
        Objects.requireNonNull(handler, "handler");

        if (!key.ordered() && order != EventOrder.NORMAL) {
            throw new IllegalArgumentException(String.format(
                    "Cannot use explicit order for unordered event: %s",
                    key.type().getName()
            ));
        }

        CopyOnWriteArrayList<RegisteredHandler<?>> registeredHandlers = this.handlers.computeIfAbsent(
                key,
                _ -> new CopyOnWriteArrayList<>()
        );

        registeredHandlers.add(new RegisteredHandler<>(order, handler));

        if (key.ordered()) {
            registeredHandlers.sort(Comparator.comparingInt(value -> value.order().ordinal()));
        }
    }

    @Override
    public <E extends Event> void fire(EventKey<O, E> key, E event) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(event, "event");

        if (!key.type().isInstance(event)) {
            throw new IllegalArgumentException(String.format(
                    "Event type mismatch. Expected %s, got %s",
                    key.type().getName(),
                    event.getClass().getName()
            ));
        }

        List<RegisteredHandler<?>> registeredHandlers = this.handlers.get(key);

        if (registeredHandlers == null || registeredHandlers.isEmpty()) {
            return;
        }

        for (RegisteredHandler<?> registeredHandler : registeredHandlers) {
            handle(registeredHandler, event);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Event> void handle(RegisteredHandler<?> registeredHandler, E event) {
        RegisteredHandler<E> typedHandler = (RegisteredHandler<E>) registeredHandler;
        typedHandler.handle(event);
    }

    private static final class RegisteredHandler<E extends Event> {

        private final EventOrder order;
        private final EventHandler<E> handler;

        private RegisteredHandler(EventOrder order, EventHandler<E> handler) {
            this.order = Objects.requireNonNull(order, "order");
            this.handler = Objects.requireNonNull(handler, "handler");
        }

        private EventOrder order() {
            return this.order;
        }

        private void handle(E event) {
            this.handler.handle(event);
        }
    }
}
