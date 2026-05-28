package xyz.endelith.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.kyori.adventure.text.format.Style;

public record ChatType(ChatTypeDecoration chat, ChatTypeDecoration narration) {

    public ChatType {
        Objects.requireNonNull(chat, "chat");
        Objects.requireNonNull(narration, "narration");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
            .chat(this.chat)
            .narration(this.narration);
    }

    public static final class Builder {

        private ChatTypeDecoration chat;
        private ChatTypeDecoration narration;

        private Builder() {
        }

        public Builder chat(ChatTypeDecoration chat) {
            this.chat = Objects.requireNonNull(chat, "chat");
            return this;
        }

        public Builder narration(ChatTypeDecoration narration) {
            this.narration = Objects.requireNonNull(narration, "narration");
            return this;
        }

        public ChatType build() {
            return new ChatType(
                Objects.requireNonNull(this.chat, "chat"),
                Objects.requireNonNull(this.narration, "narration")
            );
        }
    }

    public record ChatTypeDecoration(String translationKey, List<Parameter> parameters, Style style) {

        public ChatTypeDecoration {
            Objects.requireNonNull(translationKey, "translation key");
            Objects.requireNonNull(style, "style");

            parameters = List.copyOf(Objects.requireNonNull(parameters, "parameters"));
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder toBuilder() {
            return new Builder()
                .translationKey(this.translationKey)
                .parameters(this.parameters)
                .style(this.style);
        }

        public static final class Builder {

            private String translationKey;
            private List<Parameter> parameters = new ArrayList<>();
            private Style style = Style.empty();

            private Builder() {
            }

            public Builder translationKey(String translationKey) {
                this.translationKey = Objects.requireNonNull(translationKey, "translation key");
                return this;
            }

            public Builder parameters(List<Parameter> parameters) {
                this.parameters = new ArrayList<>(Objects.requireNonNull(parameters, "parameters"));
                return this;
            }

            public Builder parameter(Parameter parameter) {
                this.parameters.add(Objects.requireNonNull(parameter, "parameter"));
                return this;
            }

            public Builder style(Style style) {
                this.style = Objects.requireNonNull(style, "style");
                return this;
            }

            public ChatTypeDecoration build() {
                return new ChatTypeDecoration(
                    Objects.requireNonNull(this.translationKey, "translation key"),
                    this.parameters,
                    Objects.requireNonNull(this.style, "style")
                );
            }
        }

        public enum Parameter {
            SENDER,
            TARGET,
            CONTENT;
        }
    }
}
