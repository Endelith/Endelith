package xyz.endelith.event.lifecycle;

import java.util.Objects;
import xyz.endelith.MinecraftServer;
import xyz.endelith.event.Event;

public record ServerReadyEvent(MinecraftServer server) implements Event {

    public ServerReadyEvent {
        Objects.requireNonNull(server, "server");
    }
}
