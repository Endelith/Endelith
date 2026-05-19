package xyz.endelith.testplugin;

import xyz.endelith.plugin.Plugin;
import xyz.endelith.plugin.bootstrap.BootstrapContext;

public final class TestPlugin extends Plugin {

    @Override
    public void bootstrap(BootstrapContext context) {
        logger().info("Welcome plugin currently in bootstrap step");
        logger().info("You can register commands and modify registries in here!");
    }

    @Override
    public void onEnable() {
        logger().info("I am currently on actual starting state");
        logger().info("You can do whatever you want in here!");
    }

    @Override
    public void onDisable() {
        logger().info("I am currently on disabling state");
        logger().info("You can make cleanup thinks before server fully shutting down!");
    }
}
