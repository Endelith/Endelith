package xyz.endelith.network;

import net.kyori.adventure.text.Component;
import xyz.endelith.MinecraftServer;

public interface PlayerConnection {
    MinecraftServer server();
    void disconnect(Component message);
}
