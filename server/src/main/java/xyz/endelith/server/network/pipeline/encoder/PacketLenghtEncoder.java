package xyz.endelith.server.network.pipeline.encoder;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;

@NullMarked
public class PacketLenghtEncoder extends MessageToByteEncoder<ByteBuf> {

    private final PlayerConnectionImpl connection;

    public PacketLenghtEncoder(PlayerConnectionImpl connection) {
        this.connection = Objects.requireNonNull(connection, "connection");
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception { 
        StreamCodec.VAR_INT.write(out, msg.readableBytes());
        out.writeBytes(msg);    
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.connection.uncaughtException(Thread.currentThread(), cause);
    }
}
