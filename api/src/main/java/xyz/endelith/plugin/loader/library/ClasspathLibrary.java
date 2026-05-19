package xyz.endelith.plugin.loader.library;

public interface ClasspathLibrary {

    void register(LibraryStore store) throws LibraryLoadingException;
}
