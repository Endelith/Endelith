package xyz.endelith.event.lifecycle;

import java.util.Objects;
import xyz.endelith.MinecraftServer;
import xyz.endelith.event.Event;

public record ServerInitializedEvent(MinecraftServer server) implements Event {

    public ServerInitializedEvent {
        Objects.requireNonNull(server, "server");
    }
}
