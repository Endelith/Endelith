package xyz.endelith.util.color;

import net.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.Range;

public record Color(int value) implements RGBLike {

    private static final byte RED_SHIFT = Byte.SIZE * 2;
    private static final byte GREEN_SHIFT = Byte.SIZE;
    private static final int MAX_RGB_VALUE = 255;

    @Override
    public @Range(from = 0L, to = 255L) int red() {
        return (this.value >> RED_SHIFT) & MAX_RGB_VALUE;
    }

    @Override
    public @Range(from = 0L, to = 255L) int green() {
        return (this.value >> GREEN_SHIFT) & MAX_RGB_VALUE;
    }

    @Override
    public @Range(from = 0L, to = 255L) int blue() {
        return this.value & MAX_RGB_VALUE;
    }

    public static Color fromRgb(int red, int green, int blue) {
        int value = (red << RED_SHIFT) & MAX_RGB_VALUE;
        value |= (green << GREEN_SHIFT) & MAX_RGB_VALUE;
        value |= blue & MAX_RGB_VALUE;
        return new Color(value);
    }
}
