package xyz.endelith;

import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.plugin.PluginManager;

public interface MinecraftServer  {

    String brandName();

    String minecraftVersion();

    int protocolVersion();

    PluginManager pluginManager();

    ServerConfiguration configuration();

    void shutdown();
}
