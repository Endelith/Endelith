package xyz.endelith.event;

@FunctionalInterface
public interface EventHandler<E extends Event> {

    void handle(E event);
}
