package xyz.endelith.server.network.netty.encoder;

import java.util.Objects;

import com.velocitypowered.natives.compression.VelocityCompressor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;

public final class CompressionEncoder extends MessageToByteEncoder<ByteBuf> {

    private final PlayerConnectionImpl connection;
    private final VelocityCompressor compressor;

    public CompressionEncoder(PlayerConnectionImpl connection, VelocityCompressor compressor) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.compressor = Objects.requireNonNull(compressor, "compressor");
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int uncompressedSize = msg.readableBytes();
        int threshold = connection.server().configuration().compressionThreshold();

        if (uncompressedSize < threshold) {
            StreamCodec.VAR_INT.write(out, 0);
            out.writeBytes(msg);
            return;
        }

        StreamCodec.VAR_INT.write(out, uncompressedSize);
        compressor.deflate(msg, out);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        compressor.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        connection.uncaughtException(Thread.currentThread(), cause);
    }
}
