package xyz.endelith.util.ping;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public record ServerListPing(
        Version version,
        @Nullable Players players,
        @Nullable Component description,
        @Nullable Favicon favicon,
        boolean enforcesSecureChat
) {

    public ServerListPing {
        Objects.requireNonNull(version, "version");
    }

    public record Version(String name, int protocolVersion) {

        public Version {
            Objects.requireNonNull(name, "name");
        }
    }

    public record Players(int maximumPlayers, int onlinePlayers, List<Sample> samples) {

        public Players {
            samples = List.copyOf(Objects.requireNonNull(samples, "samples"));
        }

        public record Sample(String name, UUID uuid) {

            public Sample {
                Objects.requireNonNull(name, "name");
                Objects.requireNonNull(uuid, "uuid");
            }
        }
    }

    public record Favicon(String image) {

        public Favicon {
            Objects.requireNonNull(image, "image");

            try {
                Base64.getDecoder().decode(image);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("The image was not encoded with base64", e);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .version(this.version)
                .players(this.players)
                .description(this.description)
                .favicon(this.favicon)
                .enforcesSecureChat(this.enforcesSecureChat);
    }

    public static final class Builder {

        private @Nullable Version version;
        private @Nullable Players players;
        private @Nullable Component description;
        private @Nullable Favicon favicon;
        private boolean enforcesSecureChat;

        public Builder version(Version version) {
            this.version = Objects.requireNonNull(version, "version");
            return this;
        }

        public Builder players(@Nullable Players players) {
            this.players = players;
            return this;
        }

        public Builder description(@Nullable Component description) {
            this.description = description;
            return this;
        }

        public Builder favicon(@Nullable Favicon favicon) {
            this.favicon = favicon;
            return this;
        }

        public Builder enforcesSecureChat(boolean enforcesSecureChat) {
            this.enforcesSecureChat = enforcesSecureChat;
            return this;
        }

        public ServerListPing build() {
            return new ServerListPing(
                    Objects.requireNonNull(this.version, "version"),
                    this.players,
                    this.description,
                    this.favicon,
                    this.enforcesSecureChat
            );
        }
    }
}
