package xyz.endelith.testplugin;

import xyz.endelith.plugin.Plugin;
import xyz.endelith.plugin.bootstrap.BootstrapContext;

public final class TestPlugin extends Plugin {

    @Override
    public void bootstrap(BootstrapContext context) {
        logger().info("Hello World");
    }

    @Override
    public void onEnable() {
        logger().info("I enabled");
    }
}
