package xyz.endelith.event;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import xyz.endelith.event.events.Event;
import xyz.endelith.event.handler.EventHandler;
import xyz.endelith.event.handler.EventHandlers;
import xyz.endelith.event.order.EventOrder;

public final class EventManager {
    
    private final Map<Class<? extends Event>, EventHandlers<? extends Event>> handlersMap = new ConcurrentHashMap<>();

    public <E extends Event> void subscribe(Class<E> eventClass, EventOrder order, EventHandler<E> handler) {
        Objects.requireNonNull(eventClass, "event class");
        Objects.requireNonNull(order, "order");
        Objects.requireNonNull(handler, "event handler");

        @SuppressWarnings("unchecked")
        var handlers = (EventHandlers<E>) handlersMap.computeIfAbsent(eventClass, _ -> new EventHandlers<>());
        handlers.add(order, handler);
    }

    public <E extends Event> void subscribe(Class<E> eventClass, EventHandler<E> handler) {
        subscribe(eventClass, EventOrder.NORMAL, handler);
    }

    public <E extends Event> void unsubscribe(Class<E> eventClass, EventHandler<E> handler) {
        Objects.requireNonNull(eventClass, "eventClass");
        Objects.requireNonNull(handler, "handler");

        @SuppressWarnings("unchecked")
        var handlers = (EventHandlers<E>) handlersMap.get(eventClass);

        if (handlers != null) {
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                handlersMap.remove(eventClass);
            }
        }
    }

    public <E extends Event> void call(E event) {
        Objects.requireNonNull(event, "event");
        
        @SuppressWarnings("unchecked")
        var handlers = (EventHandlers<E>) handlersMap.get(event.getClass());

        if (handlers != null) {
            handlers.call(event);
        }
    }
}
