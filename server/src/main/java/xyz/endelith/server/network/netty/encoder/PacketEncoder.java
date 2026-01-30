package xyz.endelith.server.network.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.Objects;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.PacketParser;
import xyz.endelith.server.network.packet.server.ServerPacket;

public final class PacketEncoder extends MessageToByteEncoder<ServerPacket> {

    private final PlayerConnectionImpl connection;
    private final PacketParser<ServerPacket> parser;

    public PacketEncoder(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.parser = new PacketParser.Server();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerPacket msg, ByteBuf out) {
        var registry = this.parser.stateRegistry(this.connection.getState());
        var info = registry.byClass(msg.getClass());
        StreamCodec.VAR_INT.write(out, info.id());
        info.codec().write(out, msg);
    } 
}
