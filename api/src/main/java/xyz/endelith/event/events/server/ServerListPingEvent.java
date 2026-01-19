package xyz.endelith.event.events.server;

import java.util.Objects;

import xyz.endelith.event.events.CancellableEvent;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.util.ping.ServerListPing;

public final class ServerListPingEvent extends CancellableEvent { 
        
    private final PlayerConnection connection;
    private ServerListPing serverListPing;

    public ServerListPingEvent(PlayerConnection connection, ServerListPing serverListPing) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.serverListPing = Objects.requireNonNull(serverListPing, "server list ping");
    }

    public PlayerConnection connection() {
        return connection;
    }

    public ServerListPing getServerListPing() {
        return serverListPing;
    }

    public void setServerListPing(ServerListPing serverListPing) {
        this.serverListPing = serverListPing;
    }
}
