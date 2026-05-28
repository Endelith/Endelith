package xyz.endelith.entity.variant.cow;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.world.sound.SoundEvent;

public record CowSoundVariant(
        Holder<SoundEvent> ambientSound,
        Holder<SoundEvent> deathSound,
        Holder<SoundEvent> hurtSound,
        Holder<SoundEvent> stepSound
) {

    public CowSoundVariant {
        Objects.requireNonNull(ambientSound, "ambient sound");
        Objects.requireNonNull(deathSound, "death sound");
        Objects.requireNonNull(hurtSound, "hurt sound");
        Objects.requireNonNull(stepSound, "step sound");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .ambientSound(this.ambientSound)
                .deathSound(this.deathSound)
                .hurtSound(this.hurtSound)
                .stepSound(this.stepSound);
    }

    public static final class Builder {

        private @Nullable Holder<SoundEvent> ambientSound;
        private @Nullable Holder<SoundEvent> deathSound;
        private @Nullable Holder<SoundEvent> hurtSound;
        private @Nullable Holder<SoundEvent> stepSound;

        private Builder() {
        }

        public Builder ambientSound(Holder<SoundEvent> ambientSound) {
            this.ambientSound = Objects.requireNonNull(ambientSound, "ambient sound");
            return this;
        }

        public Builder deathSound(Holder<SoundEvent> deathSound) {
            this.deathSound = Objects.requireNonNull(deathSound, "death sound");
            return this;
        }

        public Builder hurtSound(Holder<SoundEvent> hurtSound) {
            this.hurtSound = Objects.requireNonNull(hurtSound, "hurt sound");
            return this;
        }

        public Builder stepSound(Holder<SoundEvent> stepSound) {
            this.stepSound = Objects.requireNonNull(stepSound, "step sound");
            return this;
        }

        public CowSoundVariant build() {
            return new CowSoundVariant(
                    Objects.requireNonNull(this.ambientSound, "ambient sound"),
                    Objects.requireNonNull(this.deathSound, "death sound"),
                    Objects.requireNonNull(this.hurtSound, "hurt sound"),
                    Objects.requireNonNull(this.stepSound, "step sound")
            );
        }
    }
}
