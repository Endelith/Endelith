package xyz.endelith.server.network.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.Objects;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.PacketRegistry;
import xyz.endelith.server.network.packet.server.ServerPacket;

public final class PacketEncoder extends MessageToByteEncoder<ServerPacket> {

    private final PlayerConnectionImpl connection;
    private final PacketRegistry registry;

    public PacketEncoder(PlayerConnectionImpl connection, PacketRegistry registry) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.registry = Objects.requireNonNull(registry, "registry");
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerPacket msg, ByteBuf out) {
        PacketRegistry.PacketEntry<ServerPacket> entry = this.registry.byClass(
                this.connection.state(),
                PacketRegistry.ConnectionSide.CLIENTBOUND,
                msg.getClass()
        );

        if (entry == null) {
            return;
        }

        StreamCodec.VAR_INT.write(out, entry.id());
        entry.streamCodec().write(out, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
