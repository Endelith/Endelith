package xyz.endelith.server.network.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.Objects;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.PacketRegistry;
import xyz.endelith.server.network.packet.client.ClientPacket;

public final class PacketDecoder extends ByteToMessageDecoder {

    private final PlayerConnectionImpl connection;
    private final PacketRegistry registry;

    public PacketDecoder(PlayerConnectionImpl connection, PacketRegistry registry) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.registry = Objects.requireNonNull(registry, "registry");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 1) {
            return;
        }

        in.markReaderIndex();
        int identifier = StreamCodec.VAR_INT.read(in);

        PacketRegistry.PacketEntry<ClientPacket> entry = this.registry.byId(
                this.connection.state(),
                PacketRegistry.ConnectionSide.SERVERBOUND,
                identifier
        );

        if (entry == null) {
            in.skipBytes(in.readableBytes());
            return;
        }

        ClientPacket packet = entry.streamCodec().read(in);
        out.add(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
