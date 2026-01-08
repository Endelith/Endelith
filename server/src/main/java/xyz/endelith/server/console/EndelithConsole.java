package xyz.endelith.server.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecrell.terminalconsole.SimpleTerminalConsole;

public final class EndelithConsole extends SimpleTerminalConsole {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EndelithConsole.class);

    @Override
    protected boolean isRunning() {
        return true;
    }

    @Override
    protected void runCommand(String command) {
        LOGGER.info("Running command: " + command);
    }

    @Override
    protected void shutdown() {

    }
}
