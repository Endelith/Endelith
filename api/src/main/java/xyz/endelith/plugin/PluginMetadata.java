package xyz.endelith.plugin;

import java.util.Collection;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Nullable;

public interface PluginMetadata {

    String mainClass();

    @Pattern("^[a-zA-Z0-9]+(?:[-_][a-zA-Z0-9]+)*$")
    String name();

    String version();

    @Nullable
    String description();

    @Nullable
    String loader();

    Collection<String> authors();

    Collection<Dependency> dependencies();

    interface Dependency {

        @Pattern("^[a-zA-Z0-9]+(?:[-_][a-zA-Z0-9]+)*$")
        String name();

        boolean required();
    }
}
