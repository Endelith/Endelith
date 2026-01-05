package xyz.endelith.server.network.exception;

import java.util.Objects;

import xyz.endelith.server.network.PlayerConnectionImpl;

public final class NetworkException extends RuntimeException { 
    
    private final PlayerConnectionImpl connection;

    public NetworkException(PlayerConnectionImpl connection, Throwable cause) {
        super(cause);
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    public PlayerConnectionImpl connection() {
        return connection;
    }

    @Override
    public String getMessage() {
        if (getCause() != null) {
            return String.format(
                "Network error on connection %s: %s",
                connection != null ? connection.toString() : "unknown",
                getCause().getMessage()
            );
        }
        return String.format(
            "Network error on connection %s",
            connection != null ? connection.toString() : "unknown"
        );
    }
}
