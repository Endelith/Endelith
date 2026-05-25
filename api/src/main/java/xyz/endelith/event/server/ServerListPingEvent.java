package xyz.endelith.event.server;

import java.util.Objects;
import xyz.endelith.event.Cancellable;
import xyz.endelith.event.Event;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.util.ping.ServerListPing;

public final class ServerListPingEvent implements Event, Cancellable {

    private final PlayerConnection connection;
    private ServerListPing status;

    private boolean cancelled;

    public ServerListPingEvent(PlayerConnection connection, ServerListPing status) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.status = Objects.requireNonNull(status, "status");
    }

    public PlayerConnection connection() {
        return this.connection;
    }

    public ServerListPing status() {
        return this.status;
    }

    public void setStatus(ServerListPing status) {
        this.status = Objects.requireNonNull(status, "status");
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public String toString() {
        return "ServerListPingEvent["
                + "connection=" + this.connection
                + ", status=" + this.status
                + ", cancelled=" + this.cancelled
                + "]";
    }
}
