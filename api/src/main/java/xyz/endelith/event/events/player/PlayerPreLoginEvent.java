package xyz.endelith.event.events.player;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import xyz.endelith.event.events.Event;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.network.plugin.LoginPlugin;
import xyz.endelith.network.plugin.LoginPluginMessageProcessor;
import xyz.endelith.util.profile.GameProfile;

public final class PlayerPreLoginEvent extends Event {

    private final PlayerConnection connection;
    private GameProfile profile;
    private final LoginPluginMessageProcessor pluginMessageProcessor;

    public PlayerPreLoginEvent(
        PlayerConnection connection, 
        GameProfile profile,
        LoginPluginMessageProcessor pluginMessageProcessor
    ) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.profile = Objects.requireNonNull(profile, "profile");
        this.pluginMessageProcessor = Objects.requireNonNull(pluginMessageProcessor, "plugin message processor");
    }

    public PlayerConnection connection() {
        return connection;
    }

    public GameProfile getProfile() {
        return profile;
    }

    public void setProfile(GameProfile profile) {
        this.profile = profile;
    }

    /**
     * Sends a login plugin message request. Can be useful to negotiate with modded clients or
     * proxies before moving on to the Configuration state.
     *
     * @param channel the plugin message channel
     * @param requestPayload the contents of the plugin message, can be null for empty
     * @return a CompletableFuture for the response. The thread on which it completes is asynchronous.
     */
    public CompletableFuture<LoginPlugin.Response> sendPluginRequest(String channel, byte[] requestPayload) {
        return pluginMessageProcessor.request(channel, requestPayload);
    }
}
