package xyz.endelith.registry.feature;

import java.util.Objects;

public record KnownPack(String namespace, String path, String version) {

    public KnownPack {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(version, "version");
    }
}
