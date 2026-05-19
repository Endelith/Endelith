package xyz.endelith.server.plugin.classpath;

import java.util.Objects;
import xyz.endelith.plugin.loader.PluginClasspathBuilder;
import xyz.endelith.plugin.loader.library.ClasspathLibrary;
import xyz.endelith.server.plugin.loader.PluginClassLoader;

public final class PluginClasspathBuilderImpl implements PluginClasspathBuilder {

    private final PluginLibraryStore libraryStore;

    public PluginClasspathBuilderImpl(PluginClassLoader classLoader) {
        this.libraryStore = new PluginLibraryStore(Objects.requireNonNull(classLoader, "classLoader"));
    }

    @Override
    public PluginClasspathBuilder addLibrary(ClasspathLibrary classpathLibrary) {
        Objects.requireNonNull(classpathLibrary, "classpathLibrary").register(this.libraryStore);
        return this;
    }
}
