package xyz.endelith.network;

import net.kyori.adventure.text.Component;

public interface PlayerConnection {

    void disconnect(Component reason);
}
