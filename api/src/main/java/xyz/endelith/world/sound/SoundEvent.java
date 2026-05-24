package xyz.endelith.world.sound;

import java.util.Objects;
import net.kyori.adventure.key.Key;

public record SoundEvent(Key sound) {
    public SoundEvent {
        Objects.requireNonNull(sound, "sound");
    }
}
