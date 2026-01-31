package xyz.endelith.event.ping;

import java.util.Objects;
import xyz.endelith.event.Cancelable;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.util.ping.ServerListPing;

public final class ServerListPingEvent extends Cancelable { 

    private final PlayerConnection connection;
    private ServerListPing serverListPing;

    public ServerListPingEvent(PlayerConnection connection, ServerListPing serverListPing) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.serverListPing = Objects.requireNonNull(serverListPing, "server list ping");
    }

    public PlayerConnection connection() {
        return this.connection;
    }

    public ServerListPing getServerListPing() {
        return this.serverListPing;
    }

    public void setServerListPing(ServerListPing serverListPing) {
        this.serverListPing = Objects.requireNonNull(serverListPing, "server list ping");
    }
}
