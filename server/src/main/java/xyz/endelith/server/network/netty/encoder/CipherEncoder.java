package xyz.endelith.server.network.netty.encoder;

import java.util.List;
import java.util.Objects;

import com.velocitypowered.natives.encryption.VelocityCipher;
import com.velocitypowered.natives.util.MoreByteBufUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import xyz.endelith.server.network.PlayerConnectionImpl;

public final class CipherEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final PlayerConnectionImpl connection;
    private final VelocityCipher cipher;

    public CipherEncoder(PlayerConnectionImpl connection, VelocityCipher cipher) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.cipher = Objects.requireNonNull(cipher, "cipher");
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf compatible = MoreByteBufUtils.ensureCompatible(ctx.alloc(), cipher, msg);
        cipher.process(compatible);
        out.add(compatible); 
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cipher.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        connection.uncaughtException(Thread.currentThread(), cause);
    }
}
