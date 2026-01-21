package xyz.endelith.registry.reference;

import java.util.Objects;

public final class RegistryReference<V> {

    private final String name;

    private RegistryReference(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    @Override
    public String toString() {
        return "RegistryReference{" +
                "name='" + name + '\'' +
                '}';
    }

    private static <V> RegistryReference<V> create(String name) {
        return new RegistryReference<>(name);
    }
}
