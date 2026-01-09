package xyz.endelith.event.events.server;

import java.util.Objects;

import xyz.endelith.event.events.CancellableEvent;
import xyz.endelith.network.PlayerConnection;

public final class ServerListPingEvent extends CancellableEvent { 
        
    private final PlayerConnection connection;
    private ServerListPingEvent ping;

    public ServerListPingEvent(PlayerConnection connection, ServerListPingEvent ping) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.ping = Objects.requireNonNull(ping, "ping");
    }

    public PlayerConnection connection() {
        return connection;
    }

    public ServerListPingEvent getPing() {
        return ping;
    }

    public void setPing(ServerListPingEvent ping) {
        this.ping = ping;
    }
}
