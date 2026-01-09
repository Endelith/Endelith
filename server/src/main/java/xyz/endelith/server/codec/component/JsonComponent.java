package xyz.endelith.server.codec.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.cosine.transcoder.Transcoder;

public final class JsonComponent {

    private static final JSONComponentSerializer SERIALIZER = JSONComponentSerializer.json();

    public static final StreamCodec<Component> STREAM_CODEC = new StreamCodec<Component>() {

        @Override
        public Component read(ByteBuf buf) {
            String json = StreamCodec.STRING.read(buf);
            return SERIALIZER.deserialize(json);
        }

        @Override
        public void write(ByteBuf buf, Component value) {
            String json = SERIALIZER.serialize(value);
            StreamCodec.STRING.write(buf, json);
        } 
    };

    public static final Codec<Component> STRING_CODEC = new Codec<>() {

        @Override
        public <D> Component decode(Transcoder<D> transcoder, D value) {
            String json = value.toString();
            return SERIALIZER.deserialize(json);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <D> D encode(Transcoder<D> transcoder, Component value) {
            String json = SERIALIZER.serialize(value);
            JsonElement element = JsonParser.parseString(json);
            return (D) element;
        }
    };
}
