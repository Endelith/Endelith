package xyz.endelith.util.random;

import java.util.Objects;
import xyz.endelith.util.range.RangeUtil;

public record Weighted<V>(V value, int weight) {
    public Weighted {
        Objects.requireNonNull(value, "value");
        RangeUtil.ensureNotNegative(weight);
    }
}
