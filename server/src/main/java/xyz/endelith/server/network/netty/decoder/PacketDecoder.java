package xyz.endelith.server.network.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import java.util.Objects;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.PacketParser;
import xyz.endelith.server.network.packet.client.ClientPacket;

public final class PacketDecoder extends ByteToMessageDecoder {

    private final PlayerConnectionImpl connection;
    private final PacketParser<ClientPacket> parser;

    public PacketDecoder(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.parser = new PacketParser.Client();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 1) {
            return;
        }

        in.markReaderIndex();

        try {
            int packetId = StreamCodec.VAR_INT.read(in);
            
            ClientPacket packet = this.parser.parse(
                this.connection.getState(), 
                packetId, 
                in
            );

            out.add(packet);
        } catch (IndexOutOfBoundsException ignored) {
            in.resetReaderIndex();
        } catch (Exception e) {
            throw new CorruptedFrameException(String.format(
                "Failed to decode packet (state = %s))", 
                this.connection.getState()
            ));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
