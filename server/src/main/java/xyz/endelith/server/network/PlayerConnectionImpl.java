package xyz.endelith.server.network;

import io.netty.channel.socket.SocketChannel;
import net.kyori.adventure.text.Component;
import xyz.endelith.network.PlayerConnection;
import xyz.endelith.server.MinecraftServerImpl;

public final class PlayerConnectionImpl implements PlayerConnection {

    public PlayerConnectionImpl(SocketChannel channel, MinecraftServerImpl server) {

    }

    @Override
    public void disconnect(Component reason) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disconnect'");
    }
}
