package xyz.endelith;

import xyz.endelith.configuration.ServerConfiguration;

public interface MinecraftServer {
    String brandName();
    String minecraftVersion();
    int protocolVersion();
    ServerConfiguration serverConfiguration();
    void shutdown();
}
