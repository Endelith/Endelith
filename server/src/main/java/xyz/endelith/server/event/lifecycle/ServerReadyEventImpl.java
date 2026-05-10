package xyz.endelith.server.event.lifecycle;

import java.util.Objects;
import xyz.endelith.event.lifecycle.ServerReadyEvent;
import xyz.endelith.server.MinecraftServerImpl;

public final class ServerReadyEventImpl implements ServerReadyEvent {

    private final MinecraftServerImpl server;

    public ServerReadyEventImpl(MinecraftServerImpl server) {
        this.server = Objects.requireNonNull(server, "server");
    }

    @Override
    public MinecraftServerImpl server() {
        return this.server;
    }
}
