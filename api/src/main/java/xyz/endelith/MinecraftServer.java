package xyz.endelith;

import xyz.endelith.configuration.ServerConfiguration;

public interface MinecraftServer {
    String brandName();

    ServerConfiguration configuration();

    void shutdown();
}
