package xyz.endelith;

import xyz.endelith.event.EventManager;

public interface MinecraftServer {

    String brandName();

    String minecraftVersion();

    int protocolVersion();

    EventManager eventManager();

    void shutdown();
}
