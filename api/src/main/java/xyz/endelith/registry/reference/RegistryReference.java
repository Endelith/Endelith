package xyz.endelith.registry.reference;

import java.util.Objects;

public final class RegistryReference<V> {

    private final String name;

    private RegistryReference(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public String name() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("RegistryReference{name='%s'}", this.name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof RegistryReference<?> reference)) {
            return false;
        }

        return this.name.equals(reference.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    private static <V> RegistryReference<V> create(String name) {
        return new RegistryReference<>(name);
    }
}
