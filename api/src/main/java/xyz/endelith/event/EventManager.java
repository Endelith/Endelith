package xyz.endelith.event;

public interface EventManager {

    void register(Object listener);

    void unregister(Object listener);

    <T extends Event> T call(T event);
}
