package xyz.endelith.entity.damage;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public record DamageType(
        String messageId,
        DamageScalingType scalingType,
        float exhaustion,
        DamageEffects effects,
        DeathMessageType deathMessageType
) {

    public DamageType {
        Objects.requireNonNull(messageId, "message id");
        Objects.requireNonNull(scalingType, "scaling type");
        Objects.requireNonNull(effects, "effects");
        Objects.requireNonNull(deathMessageType, "death message type");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .messageId(this.messageId)
                .scalingType(this.scalingType)
                .exhaustion(this.exhaustion)
                .effects(this.effects)
                .deathMessageType(this.deathMessageType);
    }

    public static final class Builder {

        private @Nullable String messageId;
        private @Nullable DamageScalingType scalingType;
        private float exhaustion;
        private @Nullable DamageEffects effects;
        private @Nullable DeathMessageType deathMessageType;

        private Builder() {
        }

        public Builder messageId(String messageId) {
            this.messageId = Objects.requireNonNull(messageId, "message id");
            return this;
        }

        public Builder scalingType(DamageScalingType scalingType) {
            this.scalingType = Objects.requireNonNull(scalingType, "scaling type");
            return this;
        }

        public Builder exhaustion(float exhaustion) {
            this.exhaustion = exhaustion;
            return this;
        }

        public Builder effects(DamageEffects effects) {
            this.effects = Objects.requireNonNull(effects, "effects");
            return this;
        }

        public Builder deathMessageType(DeathMessageType deathMessageType) {
            this.deathMessageType = Objects.requireNonNull(deathMessageType, "death message type");
            return this;
        }

        public DamageType build() {
            return new DamageType(
                    Objects.requireNonNull(this.messageId, "message id"),
                    Objects.requireNonNull(this.scalingType, "scaling type"),
                    this.exhaustion,
                    Objects.requireNonNull(this.effects, "effects"),
                    Objects.requireNonNull(this.deathMessageType, "death message type")
            );
        }
    }

    public enum DamageScalingType {
        NEVER,
        WHEN_CAUSED_BY_LIVING_NON_PLAYER,
        ALWAYS;
    }

    public enum DamageEffects {
        HURT,
        THORNS,
        DROWNING,
        BURNING,
        POKING,
        FREEZING;
    }

    public enum DeathMessageType {
        DEFAULT,
        FALL_VARIANTS,
        INTENTIONAL_GAME_DESIGN;
    }
}
