package xyz.endelith.server.registry.codec.chat;

import net.kyori.adventure.text.format.Style;
import xyz.endelith.chat.ChatType;
import xyz.endelith.chat.ChatType.ChatTypeDecoration;
import xyz.endelith.chat.ChatType.ChatTypeDecoration.Parameter;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;

public final class ChatTypeCodec {

    public static final StructCodec<ChatTypeDecoration> DECORATION_CODEC = StructCodec.of(
            "translation_key", Codec.STRING, ChatTypeDecoration::translationKey,
            "parameters", Codec.enumOf(Parameter.class).list(), ChatTypeDecoration::parameters,
            "style", Codec.COMPONENT_STYLE.defaultValue(Style.empty()), ChatTypeDecoration::style,
            ChatTypeDecoration::new
    );

    public static final StructCodec<ChatType> CODEC = StructCodec.of(
            "chat", DECORATION_CODEC, ChatType::chat,
            "narration", DECORATION_CODEC, ChatType::narration,
            ChatType::new
    );

    private ChatTypeCodec() {
    }
}
