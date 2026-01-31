package xyz.endelith.server.network.handler;

import java.util.Objects;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.network.PlayerConnectionImpl;

public abstract class PacketHandler {

    protected final PlayerConnectionImpl connection;
    protected final MinecraftServerImpl server;

    public PacketHandler(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.server = connection.server();
    }
}
