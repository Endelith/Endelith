package xyz.endelith.server.plugin.bootstrap;

import java.nio.file.Path;
import org.slf4j.Logger;
import xyz.endelith.plugin.PluginMetadata;
import xyz.endelith.plugin.bootstrap.BootstrapContext;

public final class BootstrapContextImpl implements BootstrapContext {

    @Override
    public PluginMetadata metadata() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'metadata'");
    }

    @Override
    public Path dataDirectory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dataDirectory'");
    }

    @Override
    public Logger logger() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logger'");
    }

    @Override
    public Path pluginSource() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pluginSource'");
    }
}
