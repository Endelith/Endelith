package xyz.endelith.server.configuration;

import java.util.Objects;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import xyz.endelith.server.MinecraftServerImpl;

public enum ConfigurationPlaceholder {

    MINECRAFT_VERSION_NAME("minecraft-version-name", MinecraftServerImpl.MINECRAFT_VERSION),
    MINECRAFT_PROTOCOL_VERSION("minecraft-protocol-version", String.valueOf(MinecraftServerImpl.PROTOCOL_VERSION));

    private final @TagPattern String placeholderName;
    private final String value;

    ConfigurationPlaceholder(@TagPattern String placeholderName, String value) {
        this.placeholderName = Objects.requireNonNull(placeholderName, "placeholderName");
        this.value = Objects.requireNonNull(value, "value");
    }

    public @TagPattern String placeholderName() {
        return this.placeholderName;
    }

    public static TagResolver[] createTagResolvers() {
        ConfigurationPlaceholder[] placeholders = values();
        TagResolver[] tagResolvers = new TagResolver[placeholders.length];

        for (int index = 0; index < placeholders.length; index++) {
            ConfigurationPlaceholder placeholder = placeholders[index];
            tagResolvers[index] = Placeholder.unparsed(placeholder.placeholderName(), placeholder.value);
        }

        return tagResolvers;
    }
}
