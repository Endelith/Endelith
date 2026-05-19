package xyz.endelith.plugin;

import java.nio.file.Path;
import org.slf4j.Logger;
import xyz.endelith.MinecraftServer;
import xyz.endelith.plugin.bootstrap.BootstrapContext;
import xyz.endelith.plugin.bootstrap.PluginProviderContext;

public abstract class Plugin implements PluginProviderContext {

    private MinecraftServer server;
    private PluginMetadata metadata;
    private Path dataDirectory;
    private Path source;
    private Logger logger;
    private boolean enabled;

    public MinecraftServer server() {
        return this.server;
    }

    @Override
    public PluginMetadata metadata() {
        return this.metadata;
    }

    @Override
    public Path dataDirectory() {
        return this.dataDirectory;
    }

    @Override
    public Path source() {
        return this.source;
    }

    @Override
    public Logger logger() {
        return this.logger;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void bootstrap(BootstrapContext context) {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
