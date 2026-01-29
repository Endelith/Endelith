package xyz.endelith.data.generator.resource;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.SnbtPrinterTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import xyz.endelith.data.util.ResourceUtil;

public record GenericResourceGenerator(
        String name,
        List<String> exclusions,
        boolean snbt,
        Path outputFolder
) implements ResourceGenerator {

    public GenericResourceGenerator {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(exclusions, "exclusions");
        Objects.requireNonNull(outputFolder, "outputFolder");
    }

    public GenericResourceGenerator(String name, Path outputFolder) {
        this(name, List.of(), false, outputFolder);
    }

    @Override
    public void generate() {
        var result = new JsonObject();

        try {
            var files = ResourceUtil.getResourceListing(
                    MinecraftServer.class,
                    String.format("data/minecraft/%s/", name)
            );

            for (String fileName : files) {
                var file = MinecraftServer.class
                        .getClassLoader()
                        .getResourceAsStream(String.format("data/minecraft/%s/", name) + fileName);

                var scanner = new Scanner(file);
                var content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine());
                }
                scanner.close();

                if (content.length() > 0 && fileName.endsWith(".json")) {
                    var key = "minecraft:" + fileName.substring(0, fileName.length() - 5);
                    var jsonObject = GSON.fromJson(content.toString(), JsonObject.class);
                    exclusions.forEach(jsonObject::remove);
                    result.add(key, jsonObject);
                }

                if (snbt) {
                    Tag tag = Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, result);
                    new SnbtPrinterTagVisitor("    ", 0, new ArrayList<>()).visit(tag);
                }
            }

            Path outputFile = outputFolder.resolve(name + ".json");
            writeJson(result, outputFile.toString());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Failed to generate resource: " + name, e);
        }
    }
}
