package xyz.endelith.plugin.bootstrap;

import java.nio.file.Path;
import org.slf4j.Logger;
import xyz.endelith.plugin.PluginMetadata;

public interface PluginProviderContext {

    PluginMetadata metadata();

    Path dataDirectory();

    Logger logger();

    Path pluginSource();
}
