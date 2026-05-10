package xyz.endelith.server.event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import xyz.endelith.event.Cancellable;
import xyz.endelith.event.Event;
import xyz.endelith.event.EventManager;
import xyz.endelith.event.EventOrder;
import xyz.endelith.event.Subscribe;

public final class EventManagerImpl implements EventManager {

    private static final Comparator<RegisteredListener> ORDER_COMPARATOR =
            Comparator.comparingInt(l -> l.order().ordinal());

    private final Map<Class<?>, List<RegisteredListener>> listeners = new ConcurrentHashMap<>();

    @Override
    public void register(Object listener) {
        Objects.requireNonNull(listener, "listener");

        Class<?> listenerClass = listener.getClass();

        for (Method method : listenerClass.getDeclaredMethods()) {

            if (!method.isAnnotationPresent(Subscribe.class)) {
                continue;
            }

            Class<?>[] parameters = method.getParameterTypes();

            if (parameters.length != 1) {
                throw new IllegalStateException(
                        "Subscriber method must have exactly one parameter: " + method
                );
            }

            Class<?> eventType = parameters[0];

            if (!Event.class.isAssignableFrom(eventType)) {
                throw new IllegalStateException(
                        "Subscriber parameter must implement Event: " + method
                );
            }

            method.setAccessible(true);

            Subscribe subscribe = method.getAnnotation(Subscribe.class);

            MethodHandle handle;

            try {
                handle = MethodHandles.lookup().unreflect(method);
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(
                        "Failed to create MethodHandle for: " + method,
                        exception
                );
            }

            RegisteredListener registeredListener = new RegisteredListener(
                    listener,
                    handle,
                    subscribe.order(),
                    subscribe.ignoreCancelled()
            );

            this.listeners
                    .computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add(registeredListener);

            this.listeners.get(eventType).sort(ORDER_COMPARATOR);
        }
    }

    @Override
    public void unregister(Object listener) {
        Objects.requireNonNull(listener, "listener");

        for (List<RegisteredListener> list : this.listeners.values()) {
            list.removeIf(l -> l.listener() == listener);
        }
    }

    @Override
    public <T extends Event> T call(T event) {
        Objects.requireNonNull(event, "event");

        List<RegisteredListener> list = this.listeners.get(event.getClass());

        if (list == null || list.isEmpty()) {
            return event;
        }

        boolean cancellable = event instanceof Cancellable;
        Cancellable cancellableEvent = cancellable ? (Cancellable) event : null;

        for (RegisteredListener listener : list) {

            if (cancellable
                    && cancellableEvent.isCancelled()
                    && listener.ignoreCancelled()) {
                continue;
            }

            try {
                listener.handle().invoke(listener.listener(), event);
            } catch (Throwable throwable) {
                throw new RuntimeException(
                        "Event dispatch failed: " + event.getClass().getSimpleName(),
                        throwable
                );
            }
        }

        return event;
    }

    private record RegisteredListener(
            Object listener,
            MethodHandle handle,
            EventOrder order,
            boolean ignoreCancelled
    ) {
        RegisteredListener {
            Objects.requireNonNull(listener, "listener");
            Objects.requireNonNull(handle, "handle");
            Objects.requireNonNull(order, "order");
        }
    }
}
