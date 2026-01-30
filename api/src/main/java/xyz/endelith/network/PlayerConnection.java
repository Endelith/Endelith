package xyz.endelith.network;

import java.net.SocketAddress;
import net.kyori.adventure.text.Component;
import xyz.endelith.MinecraftServer;

public interface PlayerConnection {
    
    MinecraftServer server();

    SocketAddress address();

    void disconnect(Component reason);
}
