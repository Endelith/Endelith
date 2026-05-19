package xyz.endelith.plugin.loader;

import xyz.endelith.plugin.bootstrap.PluginProviderContext;
import xyz.endelith.plugin.loader.library.ClasspathLibrary;

public interface PluginClasspathBuilder {

    PluginClasspathBuilder addLibrary(ClasspathLibrary classpathLibrary);

    PluginProviderContext context();
}
