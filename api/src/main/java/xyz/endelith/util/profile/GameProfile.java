package xyz.endelith.util.profile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.jspecify.annotations.Nullable;

public record GameProfile(UUID uuid, String name, List<Property> properties) { 
    
    public GameProfile {
        Objects.requireNonNull(uuid, "uuid");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(properties, "properties");
        if (name.length() > 16)
            throw new IllegalArgumentException("Name length cannot be greater than 16 characters");
        properties = List.copyOf(properties);
    }

    public GameProfile(UUID uuid, String name) {
        this(uuid, name, List.of());
    }

    public record Property(String name, String value, @Nullable String signature) {
        public Property {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");
        }

        public Property(String name, String value) {
            this(name, value, null);
        }
    }
}
