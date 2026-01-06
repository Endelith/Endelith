package xyz.endelith.server.network.netty.handler;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

@NullMarked
public class PacketHandler extends SimpleChannelInboundHandler<ClientPacket> {

    private final PlayerConnectionImpl connection;

    public PacketHandler(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket msg) throws Exception {
        msg.handle(connection);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
