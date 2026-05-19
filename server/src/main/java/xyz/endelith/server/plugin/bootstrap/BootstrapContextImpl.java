package xyz.endelith.server.plugin.bootstrap;

import java.nio.file.Path;
import java.util.Objects;
import org.slf4j.Logger;
import xyz.endelith.plugin.PluginMetadata;
import xyz.endelith.plugin.bootstrap.BootstrapContext;

public record BootstrapContextImpl(
        PluginMetadata metadata,
        Path dataDirectory,
        Logger logger,
        Path pluginSource
) implements BootstrapContext {
    public BootstrapContextImpl {
        Objects.requireNonNull(metadata, "metadata");
        Objects.requireNonNull(dataDirectory, "data directory");
        Objects.requireNonNull(logger, "logger");
        Objects.requireNonNull(pluginSource, "plugin source");
    }
}
