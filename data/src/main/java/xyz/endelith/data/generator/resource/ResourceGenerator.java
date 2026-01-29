package xyz.endelith.data.generator.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public interface ResourceGenerator {

    Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    default void ensureDirectory(Path directory) throws IllegalStateException {
        Objects.requireNonNull(directory, "directory");
        
        if (Files.isDirectory(directory)) { 
            return; 
        } 

        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new IllegalStateException(String.format(
                "Failed to create folder for %s", 
                directory, 
                e
            ));
        }
    }

    default void writeJson(JsonObject json, String fileName) {
        Objects.requireNonNull(json, "json");
        Objects.requireNonNull(fileName, "fileName");
 
        File outFile = this.outputFolder().resolve(fileName).toFile();

        try (FileWriter writer = new FileWriter(outFile)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write JSON to " + outFile, e);
        }
    }

    Path outputFolder();

    default void generate() {
        throw new UnsupportedOperationException(String.format(
            "This generator `%s` does not implement the generate method",
            getClass().getSimpleName())
        );
    }
}
