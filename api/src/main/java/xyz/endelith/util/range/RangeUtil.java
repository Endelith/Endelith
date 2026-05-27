package xyz.endelith.util.range;

public final class RangeUtil {

    private RangeUtil() {
    }

    public static void ensureInRange(int min, int max, int value) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("The value must be within range [" + min + ";" + max + "]");
        }
    }

    public static void ensureNotNegative(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value must not be negative");
        }
    }

    public static void ensureNotNegative(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value must not be negative");

        }
    }
}
