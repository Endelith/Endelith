package xyz.endelith.network.plugin;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.Nullable;

public final class LoginPlugin {
    public record Request(String channel, byte[] payload, CompletableFuture<Response> responseFuture) {
        public Request {
            Objects.requireNonNull(channel, "channel");
            Objects.requireNonNull(payload, "payload");
            Objects.requireNonNull(responseFuture, "response future");
        }

        public Request(String channel, byte[] requestPayload) {
            this(channel, requestPayload, new CompletableFuture<>());
        }
    }

    public record Response(String channel, @Nullable byte[] payload) {
        public Response {
            Objects.requireNonNull(channel, "channel");
        }
    }
}
