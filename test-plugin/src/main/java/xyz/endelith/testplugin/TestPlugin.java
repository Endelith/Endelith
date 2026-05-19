package xyz.endelith.testplugin;

import xyz.endelith.plugin.Plugin;
import xyz.endelith.plugin.bootstrap.BootstrapContext;

public final class TestPlugin extends Plugin {

    @Override
    public void bootstrap(BootstrapContext context) {
        logger().info("Welcome! The plugin is currently in the bootstrap step.");
        logger().info("You can register commands and modify registries here!");
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
