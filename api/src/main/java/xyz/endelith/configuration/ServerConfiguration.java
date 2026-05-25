package xyz.endelith.configuration;

import net.kyori.adventure.text.Component;

public interface ServerConfiguration {

    Component serverListDescription();

    int maximumPlayers();
}
