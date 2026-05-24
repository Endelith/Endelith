package xyz.endelith.testplugin;

import net.kyori.adventure.key.Key;
import xyz.endelith.entity.variant.cat.CatVariant;
import xyz.endelith.plugin.Plugin;
import xyz.endelith.plugin.bootstrap.BootstrapContext;
import xyz.endelith.registry.event.RegistryEvents;

public final class TestPlugin extends Plugin {

    @Override
    public void bootstrap(BootstrapContext context) {
        logger().info("Welcome! The plugin is currently in the bootstrap step.");
        logger().info("You can register commands and modify registries here!");

        context.eventManager().listen(RegistryEvents.CAT_VARIANT.compose(), event -> {
            event.register(
                    Key.key("endelith", "ash_gray"),
                    new CatVariant(
                            Key.key("endelith", "textures/entity/cat/ash_gray"),
                            Key.key("endelith", "textures/entity/cat/ash_gray_baby")
                    ),
                    null
            );
        });

        context.eventManager().listen(
                RegistryEvents.CAT_VARIANT.entryAdd(Key.key("minecraft", "tabby")),
                event -> {
                    event.builder().setValue(new CatVariant(
                            Key.key("endelith", "textures/entity/cat/custom_tabby"),
                            Key.key("endelith", "textures/entity/cat/custom_tabby_baby")
                    ));
                }
        );
    }

    @Override
    public void onEnable() {
        logger().info("I am currently in the actual startup state.");
        logger().info("You can do whatever you want here!");
    }

    @Override
    public void onDisable() {
        logger().info("I am currently in the disabling state.");
        logger().info("You can clean things up before the server fully shuts down!");
    }
}
