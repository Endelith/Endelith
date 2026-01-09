package xyz.endelith;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public enum ApiVersion {

    UNKNOWN("unknown", 000, ReleaseType.UNKNOWN),
    V1_21_10("1.21.10", 773, ReleaseType.RELEASE);

    public static final ApiVersion LATEST = values()[ApiVersion.values().length - 1];
    public static final ApiVersion LATEST_SNAPSHOT = latestByType(ReleaseType.SNAPSHOT);
    public static final ApiVersion LATEST_PRE_RELEASE = latestByType(ReleaseType.PRE_RELEASE);
    public static final ApiVersion LATEST_RELEASE_CANDIDATE = latestByType(ReleaseType.RELEASE_CANDIDATE);
    public static final ApiVersion LATEST_RELEASE = latestByType(ReleaseType.RELEASE);

    private static final Map<String, ApiVersion> NAME_VERSION_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(ApiVersion::nameValue, v -> v));

    private final String name;
    private final int protocolVersion;
    private final ReleaseType releaseType;

    ApiVersion(String name, int protocolVersion, ReleaseType releaseType) {
        this.name = Objects.requireNonNull(name, "name");
        this.protocolVersion = Objects.requireNonNull(protocolVersion, "protocol version");
        this.releaseType = Objects.requireNonNull(releaseType, "release type");
    }

    public String nameValue() {
        return name;
    }

    public int protocolVersion() {
        return protocolVersion;
    }

    public ReleaseType releaseType() {
        return releaseType;
    }

    public boolean isLatest() {
        return this == LATEST;
    }

    public boolean isNewerThan(ApiVersion other) {
        return ordinal() > other.ordinal();
    }

    public boolean isAtLeast(ApiVersion other) {
        return ordinal() >= other.ordinal();
    }

    public boolean isOlderThan(ApiVersion other) {
        return ordinal() < other.ordinal();
    }

    public boolean isAtMost(ApiVersion other) {
        return ordinal() <= other.ordinal();
    }

    public static ApiVersion byName(String name) {
        return NAME_VERSION_MAP.getOrDefault(name, UNKNOWN);
    }

    private static ApiVersion latestByType(ReleaseType type) {
        return Arrays.stream(values())
            .filter(apiVersion -> apiVersion.releaseType == type)
            .sorted((a, b) -> Integer.compare(b.ordinal(), a.ordinal()))
            .findFirst()
            .orElse(UNKNOWN);
    }

    public enum ReleaseType {
        UNKNOWN,
        SNAPSHOT,
        PRE_RELEASE,
        RELEASE_CANDIDATE,
        RELEASE,
    }
}
