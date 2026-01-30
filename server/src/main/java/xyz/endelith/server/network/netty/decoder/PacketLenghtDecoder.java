package xyz.endelith.server.network.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import xyz.endelith.cosine.stream.StreamCodec;

public final class PacketLenghtDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        buffer.markReaderIndex();

        final int length;
        try {
            length = StreamCodec.VAR_INT.read(buffer);
        } catch (IndexOutOfBoundsException ignored) { 
            buffer.resetReaderIndex();
            return;
        } catch (Exception e) {
            throw new CorruptedFrameException("Invalid VarInt packet length", e);
        }

        if (length < 0) {
            throw new CorruptedFrameException("Negative packet length: " + length);
        }

        if (buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return;
        }

        out.add(buffer.readRetainedSlice(length));
    }
}
