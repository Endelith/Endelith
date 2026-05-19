package xyz.endelith.plugin.loader.library.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import xyz.endelith.plugin.loader.library.ClasspathLibrary;
import xyz.endelith.plugin.loader.library.LibraryLoadingException;
import xyz.endelith.plugin.loader.library.LibraryStore;

public record JarLibrary(Path path) implements ClasspathLibrary {

    public JarLibrary {
        Objects.requireNonNull(path, "path");
    }

    @Override
    public void register(LibraryStore store) throws LibraryLoadingException {
        if (Files.notExists(this.path)) {
            throw new LibraryLoadingException("Could not find library at " + this.path);
        }

        store.addLibrary(this.path);
    }
}
