package xyz.endelith.util.number;

import java.util.List;
import java.util.Objects;
import xyz.endelith.util.random.Weighted;

public interface IntProvider {

    record Constant(int value) implements IntProvider {}

    record Clamped(IntProvider source, int minimum, int maximum) implements IntProvider {
        public Clamped {
            Objects.requireNonNull(source, "source");
        }
    }

    record ClampedNormal(float mean, float deviation, int minimum, int maximum) implements IntProvider {}

    record Uniform(int minimum, int maximum) implements IntProvider {}

    record BiasedToBottom(int minimum, int maximum) implements IntProvider {}

    record WeightedRandom(List<Weighted<IntProvider>> distribution) implements IntProvider {
        public WeightedRandom {
            distribution = List.copyOf(Objects.requireNonNull(distribution, "distribution"));
            if (distribution.isEmpty()) {
                throw new IllegalArgumentException("The weighted list must not be empty");
            }
        }
    }
}
