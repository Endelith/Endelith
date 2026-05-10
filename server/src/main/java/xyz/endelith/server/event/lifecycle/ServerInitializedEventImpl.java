package xyz.endelith.server.event.lifecycle;

import java.util.Objects;
import xyz.endelith.event.lifecycle.ServerInitializedEvent;
import xyz.endelith.server.MinecraftServerImpl;

public final class ServerInitializedEventImpl implements ServerInitializedEvent {

    private final MinecraftServerImpl server;

    public ServerInitializedEventImpl(MinecraftServerImpl server) {
        this.server = Objects.requireNonNull(server, "server");
    }

    @Override
    public MinecraftServerImpl server() {
        return this.server;
    }
}
