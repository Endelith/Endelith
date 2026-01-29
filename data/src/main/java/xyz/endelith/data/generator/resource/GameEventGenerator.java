package xyz.endelith.data.generator.resource;

import java.nio.file.Path;
import java.util.Objects;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;

public record GameEventGenerator(
    Path outputFolder
) implements ResourceGenerator {

    public GameEventGenerator { 
        Objects.requireNonNull(outputFolder, "output folder");
    }

    @Override
    public void generate() {
        ensureDirectory(outputFolder);
        
        JsonObject gameEvents = new JsonObject();
        var registry = BuiltInRegistries.GAME_EVENT;
        for (var gameEvent : registry) {
            final var location = registry.getKey(gameEvent);
            JsonObject gameEventJson = new JsonObject();
            gameEventJson.addProperty("id", registry.getId(gameEvent));
            gameEventJson.addProperty("notificationRadius", gameEvent.notificationRadius());
            gameEvents.add(location.toString(), gameEventJson);
        }

        writeJson(gameEvents, "game_event.json");
    }
}
