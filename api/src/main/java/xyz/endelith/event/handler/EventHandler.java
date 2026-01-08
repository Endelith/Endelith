package xyz.endelith.event.handler;

import xyz.endelith.event.events.Event;

@FunctionalInterface
public interface EventHandler<E extends Event> {
    void handle(E event);
}
