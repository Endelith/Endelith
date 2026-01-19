package xyz.endelith.configuration;

import net.kyori.adventure.text.Component;

public interface ServerConfiguration {
    String address();
    int port();
    int maximumPlayers();
    Component serverListDescription();
    boolean onlineMode();
}
