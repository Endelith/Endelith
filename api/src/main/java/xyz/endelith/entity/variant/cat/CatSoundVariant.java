package xyz.endelith.entity.variant.cat;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.world.sound.SoundEvent;

public record CatSoundVariant(SoundProfile adultSounds, SoundProfile babySounds) {

    public CatSoundVariant {
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

        public CatSoundVariant build() {
            return new CatSoundVariant(
                    Objects.requireNonNull(this.adultSounds, "adult sounds"),
                    Objects.requireNonNull(this.babySounds, "baby sounds")
            );
        }
    }

    public record SoundProfile(
            Holder<SoundEvent> ambientSound,
            Holder<SoundEvent> begForFoodSound,
            Holder<SoundEvent> deathSound,
            Holder<SoundEvent> eatSound,
            Holder<SoundEvent> hissSound,
            Holder<SoundEvent> hurtSound,
            Holder<SoundEvent> purrSound,
            Holder<SoundEvent> purreowSound,
            Holder<SoundEvent> strayAmbientSound
    ) {

        public SoundProfile {
            Objects.requireNonNull(ambientSound, "ambient sound");
            Objects.requireNonNull(begForFoodSound, "beg for food sound");
            Objects.requireNonNull(deathSound, "death sound");
            Objects.requireNonNull(eatSound, "eat sound");
            Objects.requireNonNull(hissSound, "hiss sound");
            Objects.requireNonNull(hurtSound, "hurt sound");
            Objects.requireNonNull(purrSound, "purr sound");
            Objects.requireNonNull(purreowSound, "purreow sound");
            Objects.requireNonNull(strayAmbientSound, "stray ambient sound");
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder toBuilder() {
            return builder()
                    .ambientSound(this.ambientSound)
                    .begForFoodSound(this.begForFoodSound)
                    .deathSound(this.deathSound)
                    .eatSound(this.eatSound)
                    .hissSound(this.hissSound)
                    .hurtSound(this.hurtSound)
                    .purrSound(this.purrSound)
                    .purreowSound(this.purreowSound)
                    .strayAmbientSound(this.strayAmbientSound);
        }

        public static final class Builder {

            private @Nullable Holder<SoundEvent> ambientSound;
            private @Nullable Holder<SoundEvent> begForFoodSound;
            private @Nullable Holder<SoundEvent> deathSound;
            private @Nullable Holder<SoundEvent> eatSound;
            private @Nullable Holder<SoundEvent> hissSound;
            private @Nullable Holder<SoundEvent> hurtSound;
            private @Nullable Holder<SoundEvent> purrSound;
            private @Nullable Holder<SoundEvent> purreowSound;
            private @Nullable Holder<SoundEvent> strayAmbientSound;

            private Builder() {
            }

            public Builder ambientSound(Holder<SoundEvent> ambientSound) {
                this.ambientSound = Objects.requireNonNull(ambientSound, "ambient sound");
                return this;
            }

            public Builder begForFoodSound(Holder<SoundEvent> begForFoodSound) {
                this.begForFoodSound = Objects.requireNonNull(begForFoodSound, "beg for food sound");
                return this;
            }

            public Builder deathSound(Holder<SoundEvent> deathSound) {
                this.deathSound = Objects.requireNonNull(deathSound, "death sound");
                return this;
            }

            public Builder eatSound(Holder<SoundEvent> eatSound) {
                this.eatSound = Objects.requireNonNull(eatSound, "eat sound");
                return this;
            }

            public Builder hissSound(Holder<SoundEvent> hissSound) {
                this.hissSound = Objects.requireNonNull(hissSound, "hiss sound");
                return this;
            }

            public Builder hurtSound(Holder<SoundEvent> hurtSound) {
                this.hurtSound = Objects.requireNonNull(hurtSound, "hurt sound");
                return this;
            }

            public Builder purrSound(Holder<SoundEvent> purrSound) {
                this.purrSound = Objects.requireNonNull(purrSound, "purr sound");
                return this;
            }

            public Builder purreowSound(Holder<SoundEvent> purreowSound) {
                this.purreowSound = Objects.requireNonNull(purreowSound, "purreow sound");
                return this;
            }

            public Builder strayAmbientSound(Holder<SoundEvent> strayAmbientSound) {
                this.strayAmbientSound = Objects.requireNonNull(strayAmbientSound, "stray ambient sound");
                return this;
            }

            public SoundProfile build() {
                return new SoundProfile(
                        Objects.requireNonNull(this.ambientSound, "ambient sound"),
                        Objects.requireNonNull(this.begForFoodSound, "beg for food sound"),
                        Objects.requireNonNull(this.deathSound, "death sound"),
                        Objects.requireNonNull(this.eatSound, "eat sound"),
                        Objects.requireNonNull(this.hissSound, "hiss sound"),
                        Objects.requireNonNull(this.hurtSound, "hurt sound"),
                        Objects.requireNonNull(this.purrSound, "purr sound"),
                        Objects.requireNonNull(this.purreowSound, "purreow sound"),
                        Objects.requireNonNull(this.strayAmbientSound, "stray ambient sound")
                );
            }
        }
    }
}
