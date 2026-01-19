package xyz.endelith.server.network.netty.encoder;

import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.PacketParser;
import xyz.endelith.server.network.packet.server.ServerPacket;

public class PacketEncoder extends MessageToByteEncoder<ServerPacket>{

    private final PlayerConnectionImpl connection;
    private final PacketParser<ServerPacket> parser;

    public PacketEncoder(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.parser = new PacketParser.Server();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerPacket msg, ByteBuf out) throws Exception {
        var registry = parser.stateRegistry(connection.getState());
        var info = registry.byClass(msg.getClass());
        StreamCodec.VAR_INT.write(out, info.id());
        info.codec().write(out, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        connection.uncaughtException(Thread.currentThread(), cause);
    }
}
