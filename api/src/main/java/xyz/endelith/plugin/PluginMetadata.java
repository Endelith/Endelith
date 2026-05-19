package xyz.endelith.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Nullable;

public record PluginMetadata(
        String mainClass,
        @Pattern("^[a-zA-Z0-9]+(?:[-_][a-zA-Z0-9]+)*$") String name,
        String version,
        @Nullable String description,
        Collection<String> authors,
        Collection<Dependency> dependencies
) {
    public PluginMetadata {
        Objects.requireNonNull(mainClass, "main class");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(version, "version");
        authors = List.copyOf(Objects.requireNonNull(authors, "authors"));
        dependencies = List.copyOf(Objects.requireNonNull(dependencies, "dependencies"));
    }

    public record Dependency(@Pattern("^[a-zA-Z0-9]+(?:[-_][a-zA-Z0-9]+)*$") String name, boolean required) {
        public Dependency {
            Objects.requireNonNull(name, "name");
        }
    }
}
