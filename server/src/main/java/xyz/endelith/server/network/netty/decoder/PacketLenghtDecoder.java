package xyz.endelith.server.network.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import java.util.Objects;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;

public final class PacketLenghtDecoder extends ByteToMessageDecoder {

    private final PlayerConnectionImpl connection;

    public PacketLenghtDecoder(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();

        final int length;
        try {
            length = StreamCodec.VAR_INT.read(in);
        } catch (IndexOutOfBoundsException ignored) { 
            in.resetReaderIndex();
            return;
        } catch (Exception e) {
            throw new CorruptedFrameException("Invalid VarInt packet length", e);
        }

        if (length < 0) {
            throw new CorruptedFrameException("Negative packet length: " + length);
        }

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        out.add(in.readRetainedSlice(length));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
