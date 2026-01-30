package xyz.endelith;

import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.event.EventManager;

public interface MinecraftServer {
    String brandName();

    ServerConfiguration configuration();

    EventManager eventManager();

    void shutdown();
}
