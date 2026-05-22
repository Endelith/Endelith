package xyz.endelith;

import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.plugin.PluginManager;
import xyz.endelith.registry.RegistryManager;

public interface MinecraftServer  {

    String brandName();

    String minecraftVersion();

    int protocolVersion();

    ServerConfiguration configuration();

    PluginManager pluginManager();

    RegistryManager registryManager();

    void shutdown();
}
