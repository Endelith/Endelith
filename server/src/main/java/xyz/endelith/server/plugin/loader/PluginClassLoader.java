package xyz.endelith.server.plugin.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import xyz.endelith.server.MinecraftServerImpl;

public final class PluginClassLoader extends URLClassLoader {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public PluginClassLoader(File pluginSource) throws IOException {
        super(
            new URL[] {pluginSource.toURI().toURL()},
            MinecraftServerImpl.class.getClassLoader()
        );
    }
}
