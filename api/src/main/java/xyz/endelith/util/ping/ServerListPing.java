package xyz.endelith.util.ping;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.jspecify.annotations.Nullable;

import net.kyori.adventure.text.Component;

public record ServerListPing(
    Version version, 
    @Nullable Players players, 
    @Nullable Component description, 
    @Nullable Favicon favicon, 
    boolean enforcesSecureChat
) {

    public record Version(String name, int protocolVersion) {
        public Version {
            Objects.requireNonNull(name, "name");
            Objects.requireNonNull(protocolVersion, "protocol version");
        }
    }

    public record Players(int maximumPlayers, int onlinePlayers, List<Sample> sample) {
    
        public Players {
            Objects.requireNonNull(maximumPlayers, "maximum players");
            Objects.requireNonNull(onlinePlayers, "online players");
            sample = List.copyOf(Objects.requireNonNull(sample, "sample"));
        }

        public record Sample(String name, UUID uuid) {
            public Sample {
                Objects.requireNonNull(name, "name");
                Objects.requireNonNull(uuid, "uuid");;
            }
        }
    }

    public record Favicon(String image) {
        public Favicon {
            Objects.requireNonNull(image, "image");
            try {
                // Check if the image was encoded with base64 correctly
                Base64.getDecoder().decode(image);
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("The image was not encoded with base64", exception);
            }
        }
    }
}
