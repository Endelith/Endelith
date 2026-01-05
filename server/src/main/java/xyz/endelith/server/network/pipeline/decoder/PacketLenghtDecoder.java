package xyz.endelith.server.network.pipeline.decoder;

import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.NullMarked;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;

@NullMarked
public class PacketLenghtDecoder extends ByteToMessageDecoder {

    private final PlayerConnectionImpl connection;

    public PacketLenghtDecoder(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) return;
        
        in.markReaderIndex(); 
        int packetLength = StreamCodec.VAR_INT.read(in);
        
        if (packetLength > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }

        out.add(in.retainedSlice(in.readerIndex(), packetLength));
        in.skipBytes(packetLength); 
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
