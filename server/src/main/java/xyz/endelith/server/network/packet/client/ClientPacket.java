package xyz.endelith.server.network.packet.client;

import xyz.endelith.server.network.PlayerConnectionImpl;

public interface ClientPacket {
    void handle(PlayerConnectionImpl connection);
}
