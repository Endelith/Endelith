package xyz.endelith;

import xyz.endelith.event.EventManager;
import xyz.endelith.event.EventOwner;
import xyz.endelith.plugin.PluginManager;

public interface MinecraftServer extends EventOwner {

    String brandName();

    String minecraftVersion();

    int protocolVersion();

    PluginManager pluginManager();

    @Override
    EventManager<MinecraftServer> eventManager();

    void shutdown();
}
