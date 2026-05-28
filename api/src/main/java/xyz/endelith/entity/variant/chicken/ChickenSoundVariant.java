package xyz.endelith.entity.variant.chicken;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.world.sound.SoundEvent;

public record ChickenSoundVariant(SoundProfile adultSounds, SoundProfile babySounds) {

    public ChickenSoundVariant {
        Objects.requireNonNull(adultSounds, "adult sounds");
        Objects.requireNonNull(babySounds, "baby sounds");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .adultSounds(this.adultSounds)
                .babySounds(this.babySounds);
    }

    public static final class Builder {

        private @Nullable SoundProfile adultSounds;
        private @Nullable SoundProfile babySounds;

        private Builder() {
        }

        public Builder adultSounds(SoundProfile adultSounds) {
            this.adultSounds = Objects.requireNonNull(adultSounds, "adult sounds");
            return this;
        }

        public Builder babySounds(SoundProfile babySounds) {
            this.babySounds = Objects.requireNonNull(babySounds, "baby sounds");
            return this;
        }

        public ChickenSoundVariant build() {
            return new ChickenSoundVariant(
                    Objects.requireNonNull(this.adultSounds, "adult sounds"),
                    Objects.requireNonNull(this.babySounds, "baby sounds")
            );
        }
    }

    public record SoundProfile(
            Holder<SoundEvent> ambientSound,
            Holder<SoundEvent> deathSound,
            Holder<SoundEvent> hurtSound,
            Holder<SoundEvent> stepSound
    ) {

        public SoundProfile {
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

            public SoundProfile build() {
                return new SoundProfile(
                        Objects.requireNonNull(this.ambientSound, "ambient sound"),
                        Objects.requireNonNull(this.deathSound, "death sound"),
                        Objects.requireNonNull(this.hurtSound, "hurt sound"),
                        Objects.requireNonNull(this.stepSound, "step sound")
                );
            }
        }
    }
}
