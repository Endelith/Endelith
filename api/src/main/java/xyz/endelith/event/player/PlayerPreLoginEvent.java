package xyz.endelith.event.player;

import java.util.Objects;
import xyz.endelith.event.Event;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.util.profile.GameProfile;

public final class PlayerPreLoginEvent implements Event {

    private final PlayerConnection connection;
    private GameProfile profile;

    public PlayerPreLoginEvent(PlayerConnection connection, GameProfile profile) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.profile = Objects.requireNonNull(profile, "profile");
    }

    public PlayerConnection connection() {
        return this.connection;
    }

    public GameProfile profile() {
        return this.profile;
    }

    public void setProfile(GameProfile profile) {
        this.profile = Objects.requireNonNull(profile, "profile");
    }

    @Override
    public String toString() {
        return "PlayerPreLoginEvent["
                + "connection=" + this.connection
                + ", profile=" + this.profile
                + "]";
    }
}
