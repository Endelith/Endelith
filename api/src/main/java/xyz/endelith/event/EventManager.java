package xyz.endelith.event;

public interface EventManager<O extends EventOwner> {

    <E extends Event> void listen(EventKey<O, E> key, EventHandler<E> handler);

    <E extends Event> void listen(EventKey<O, E> key, EventOrder order, EventHandler<E> handler);

    <E extends Event> void fire(EventKey<O, E> key, E event);
}
