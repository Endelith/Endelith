package xyz.endelith.event.events.server;

import java.util.Objects;

import xyz.endelith.event.events.CancellableEvent;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.util.ping.ServerListPing;

public final class ServerListPingEvent extends CancellableEvent { 
        
    private final PlayerConnection connection;
    private ServerListPing ping;

    public ServerListPingEvent(PlayerConnection connection, ServerListPing ping) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.ping = Objects.requireNonNull(ping, "ping");
    }

    public PlayerConnection connection() {
        return connection;
    }

    public ServerListPing getPing() {
        return ping;
    }

    public void setPing(ServerListPing ping) {
        this.ping = ping;
    }
}
