package xyz.endelith.plugin;

import java.nio.file.Path;
import org.slf4j.Logger;
import xyz.endelith.MinecraftServer;
import xyz.endelith.plugin.bootstrap.BootstrapContext;

public abstract class Plugin {

    private MinecraftServer server;
    private PluginMetadata metadata;
    private Path dataFolder;
    private Logger logger;
    private boolean enabled;

    public MinecraftServer server() {
        return this.server;
    }

    public PluginMetadata metadata() {
        return this.metadata;
    }

    public Path dataFolder() {
        return this.dataFolder;
    }

    public Logger logger() {
        return this.logger;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void bootstrap(BootstrapContext context) {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
