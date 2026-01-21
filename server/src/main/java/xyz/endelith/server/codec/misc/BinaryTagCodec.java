package xyz.endelith.server.codec.misc;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.nbt.BinaryTagTypes;
import xyz.endelith.cosine.stream.StreamCodec;

public class BinaryTagCodec {

    private static final List<BinaryTagType<?>> ALL_TYPES = List.of(
        BinaryTagTypes.END,
        BinaryTagTypes.BYTE,
        BinaryTagTypes.SHORT,
        BinaryTagTypes.INT,
        BinaryTagTypes.LONG,
        BinaryTagTypes.FLOAT,
        BinaryTagTypes.DOUBLE,
        BinaryTagTypes.BYTE_ARRAY,
        BinaryTagTypes.STRING,
        BinaryTagTypes.LIST,
        BinaryTagTypes.COMPOUND,
        BinaryTagTypes.INT_ARRAY,
        BinaryTagTypes.LONG_ARRAY
    );

    private BinaryTagCodec() {}
   
    public static final StreamCodec<BinaryTag> STREAM_CODEC = new StreamCodec<BinaryTag>() {

        @Override
        public BinaryTag read(ByteBuf buf) {
            try (ByteBufInputStream inputStream = new ByteBufInputStream(buf)) {
                byte id = buf.readByte();
                BinaryTagType<?> type = byId(id);
                return type.read(inputStream);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void write(ByteBuf buf, BinaryTag object) {
           try (ByteBufOutputStream outputStream = new ByteBufOutputStream(buf)) {
                BinaryTagType type = object.type();
                buf.writeByte(type.id());
                type.write(object, outputStream);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } 
    };
    
    private static BinaryTagType<?> byId(byte id) {
        for (BinaryTagType<?> type : ALL_TYPES) {
            if (type.id() == id) {
                return type;
            }
        }
        throw new NoSuchElementException("No BinaryTagType with id " + id);
    }
}
