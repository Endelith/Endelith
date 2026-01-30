package xyz.endelith.event;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EventManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventManager.class);

    private final Map<Class<? extends Event>,
        Map<EventOrder, CopyOnWriteArrayList<EventHandler<? extends Event>>>>
        handlers = new ConcurrentHashMap<>();

    public <E extends Event> void subscribe(
        Class<E> eventClass, 
        EventOrder order, 
        EventHandler<E> handler
    ) {
        Objects.requireNonNull(eventClass, "event class");
        Objects.requireNonNull(order, "order");
        Objects.requireNonNull(handler, "handler");

        var orderMap = this.handlers.computeIfAbsent(
                eventClass,
                _ -> new EnumMap<>(EventOrder.class)
        );

        @SuppressWarnings("unchecked")
        var list = (CopyOnWriteArrayList<EventHandler<E>>) (CopyOnWriteArrayList<?>)
                orderMap.computeIfAbsent(
                        order,
                        _ -> new CopyOnWriteArrayList<>()
                );

        list.add(handler);
    }

    public <E extends Event> void subscribe(Class<E> eventClass, EventHandler<E> handler) {
        subscribe(eventClass, EventOrder.NORMAL, handler);
    }

    public <E extends Event> void unsubscribe(Class<E> eventClass, EventHandler<E> handler) {
        var orderMap = this.handlers.get(eventClass);
        if (orderMap != null) {
            for (var list : orderMap.values()) {
                list.remove(handler);
            }
        }
    }

    public void call(Event event) {
        var orderMap = this.handlers.get(event.getClass());
        if (orderMap == null) {
            return;
        }
    
        for (EventOrder order : EventOrder.values()) {
            var list = orderMap.get(order);
            if (list == null || list.isEmpty()) {
                continue;
            }
    
            for (var handler : list) {
                try {
                    @SuppressWarnings("unchecked")
                    EventHandler<Event> h = (EventHandler<Event>) handler; 
                    h.execute(event);
                } catch (Throwable t) {
                    LOGGER.error(
                            "Failed to handle event: {}",
                            event.getClass().getSimpleName(),
                            t
                    );
                }
            }
        }
    }
}
