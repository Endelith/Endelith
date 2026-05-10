package xyz.endelith.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.MinecraftServer;
import xyz.endelith.event.lifecycle.ServerInitializedEvent;
import xyz.endelith.event.lifecycle.ServerReadyEvent;
import xyz.endelith.event.lifecycle.ServerShutdownEvent;
import xyz.endelith.server.event.EventManagerImpl;

public final class MinecraftServerImpl implements MinecraftServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinecraftServerImpl.class);

    private static final String BRAND_NAME = "Endelith";

    public static final String MINECRAFT_VERSION = "26.1.2";
    public static final int PROTOCOL_VERSION = 775;

    private final Thread shutdownThread = createShutdownThread();
    private final EventManagerImpl eventManager = new EventManagerImpl();

    public MinecraftServerImpl() {
        try {
            Runtime.getRuntime().addShutdownHook(this.shutdownThread);
            this.eventManager.call(new ServerInitializedEvent(this));
            this.eventManager.call(new ServerReadyEvent(this));
        } catch (Throwable t) {
            LOGGER.error("an error occurred while starting the server", t);
            shutdown();
        }
    }

    public static void main(String[] args) {
        new MinecraftServerImpl();
    }

    @Override
    public String brandName() {
        return BRAND_NAME;
    }

    @Override
    public String minecraftVersion() {
        return MINECRAFT_VERSION;
    }

    @Override
    public int protocolVersion() {
        return PROTOCOL_VERSION;
    }

    @Override
    public EventManagerImpl eventManager() {
        return this.eventManager;
    }

    @Override
    public void shutdown() {
        try {
            this.shutdownThread.start();
        } catch (IllegalThreadStateException e) {
            // The shutdown has already been scheduled
        }
    }

    private Thread createShutdownThread() {
        return Thread.ofVirtual()
            .name("Shutdown Thread")
            .unstarted(() -> {
                LOGGER.info("Shutting down the server...");
                this.eventManager.call(new ServerShutdownEvent());
                LOGGER.info("Successfully shut down the server");
            });
    }
}
