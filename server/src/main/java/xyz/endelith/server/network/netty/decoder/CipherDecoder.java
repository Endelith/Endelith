package xyz.endelith.server.network.netty.decoder;

import java.util.List;
import java.util.Objects;

import com.velocitypowered.natives.encryption.VelocityCipher;
import com.velocitypowered.natives.util.MoreByteBufUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import xyz.endelith.server.network.PlayerConnectionImpl;

public final class CipherDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final PlayerConnectionImpl connection;
    private final VelocityCipher cipher;

    public CipherDecoder(PlayerConnectionImpl connection, VelocityCipher cipher) {
        this.connection = Objects.requireNonNull(connection, "connection");
        this.cipher = Objects.requireNonNull(cipher, "cipher");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if (!ctx.channel().isActive()) return; 
        ByteBuf compatible = MoreByteBufUtils.ensureCompatible(ctx.alloc(), cipher, msg);
        cipher.process(compatible);
        out.add(compatible);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        connection.uncaughtException(Thread.currentThread(), cause);
    }
}
