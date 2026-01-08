package xyz.endelith.event.handler;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.endelith.event.events.Event;
import xyz.endelith.event.order.EventOrder;

public final class EventHandlers<E extends Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlers.class);

    private final Map<EventOrder, CopyOnWriteArrayList<EventHandler<E>>> handlers = new EnumMap<>(EventOrder.class);

    public EventHandlers() {
        for (EventOrder order : EventOrder.values()) {
            handlers.put(order, new CopyOnWriteArrayList<>());
        }
    }

    public void add(EventOrder order, EventHandler<E> handler) {
        handlers.get(order).add(handler);
    }

    public void remove(EventHandler<E> handler) {
        for (var list : handlers.values()) {
            list.remove(handler);
        }
    }

    public boolean isEmpty() {
        for (var list : handlers.values()) {
            if (!list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void call(E event) {
        for (EventOrder order : EventOrder.values()) {
            for (var handler : handlers.get(order)) {
                try {
                    handler.handle(event);
                } catch(Throwable t) {
                    LOGGER.error("Failed to handle {}", event.getClass().getSimpleName(), t);
                }
            }
        }
    }
}
