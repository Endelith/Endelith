package xyz.endelith.server.console;

import java.nio.file.Paths;
import java.util.Objects;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import xyz.endelith.server.MinecraftServerImpl;

public final class EndelithConsole extends SimpleTerminalConsole {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EndelithConsole.class);

    private final MinecraftServerImpl server;

    public EndelithConsole(MinecraftServerImpl server) {
        this.server = Objects.requireNonNull(server, "server");
    }

    @Override
    protected LineReader buildReader(LineReaderBuilder builder) {
        return super.buildReader(builder
            .appName("Endelith")
            .variable(LineReader.HISTORY_FILE, Paths.get(".command_history"))
        );
    }

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
        server.shutdown();
    }
}
