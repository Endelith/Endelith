package xyz.endelith.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.endelith.MinecraftServer;
import xyz.endelith.server.network.NetworkManager;

public final class MinecraftServerImpl implements MinecraftServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinecraftServerImpl.class);
    
    private static final String BRAND_NAME = "Endelith";
    private static final String MINECRAFT_VERSION = "1.21.10";

    private static final int PROTOCOL_VERSION = 773;
 
    private final NetworkManager networkManager;

    private final Thread shutdownThread = createShutdownThread();

    public MinecraftServerImpl() {
        this.networkManager = new NetworkManager(this);
 
        try {
            Runtime.getRuntime().addShutdownHook(shutdownThread);
            networkManager.bind();
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
    public void shutdown() {
        try {
            shutdownThread.start();
        } catch (IllegalThreadStateException exception) {
            // The shutdown has already been scheduled
        }
    }

    private Thread createShutdownThread() {
        return Thread.ofVirtual()
            .name("Shutdown Thread")
            .unstarted(() -> {  
                LOGGER.info("Shutting down the server...");
                networkManager.shutdown(); 
                LOGGER.info("Successfully shut down the server");    
            });
    }
}
