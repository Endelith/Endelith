package xyz.endelith.server.network.netty.decoder;

import java.util.List;
import java.util.Objects;

import com.velocitypowered.natives.compression.VelocityCompressor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;

public final class CompressionDecoder extends ByteToMessageDecoder {

    private final PlayerConnectionImpl connection;
    private final VelocityCompressor compressor;

    public CompressionDecoder(PlayerConnectionImpl connection, VelocityCompressor compressor) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.compressor = Objects.requireNonNull(compressor, "compressor");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) return;
        
        int dataLength = StreamCodec.VAR_INT.read(in);
        
        if (dataLength == 0) {
            out.add(in.readRetainedSlice(in.readableBytes()));
            return;
        }

        ByteBuf uncompressed = ctx.alloc().buffer(dataLength);
        compressor.inflate(in, uncompressed, dataLength);
        out.add(uncompressed);
    }

    @Override
    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        compressor.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        connection.uncaughtException(Thread.currentThread(), cause);
    }
}
