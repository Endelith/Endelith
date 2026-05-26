package xyz.endelith.util.profile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public record GameProfile(UUID uuid, String username, List<Property> properties) {

    public GameProfile {
        Objects.requireNonNull(uuid, "uuid");
        Objects.requireNonNull(username, "username");
        Objects.requireNonNull(properties, "properties");

        if (username.length() > 16) {
            throw new IllegalArgumentException(String.format(
                    "Username too long: %s",
                    username.length()
            ));
        }

        if (properties.size() > 16) {
            throw new IllegalArgumentException(String.format(
                    "Too many properties: %s",
                    properties.size()
            ));
        }

        properties = List.copyOf(properties);
    }

    public GameProfile(UUID uuid, String username) {
        this(uuid, username, List.of());
    }

    public record Property(String name, String value, @Nullable String signature) {

        public Property {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(value, "value");

            if (name.length() > 64) {
                throw new IllegalArgumentException(String.format(
                        "Property name too long: %s",
                        name.length()
                ));
            }

            if (value.length() > 32767) {
                throw new IllegalArgumentException(String.format(
                        "Property value too long: %s",
                        value.length()
                ));
            }

            if (signature != null && signature.length() > 1024) {
                throw new IllegalArgumentException(String.format(
                        "Property signature too long: %s",
                        signature.length()
                ));
            }
        }
    }
}
