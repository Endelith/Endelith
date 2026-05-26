package xyz.endelith.event;

import xyz.endelith.event.player.PlayerPreLoginEvent;
import xyz.endelith.event.server.ServerListPingEvent;
import xyz.endelith.plugin.Plugin;

public final class Events {

    public static final EventKey<Plugin, ServerListPingEvent> SERVER_LIST_PING =
            EventKey.ordered(ServerListPingEvent.class);

    public static final EventKey<Plugin, PlayerPreLoginEvent> PLAYER_PRE_LOGIN =
            EventKey.ordered(PlayerPreLoginEvent.class);

    private Events() {
    }
}
