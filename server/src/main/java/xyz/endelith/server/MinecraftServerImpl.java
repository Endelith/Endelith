package xyz.endelith.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.MinecraftServer;
import xyz.endelith.server.configuration.ServerConfigurationImpl;
import xyz.endelith.server.network.NetworkManager;

public final class MinecraftServerImpl implements MinecraftServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinecraftServerImpl.class); 

    private static final String BRAND_NAME = "Endelith"; 

    private final ServerConfigurationImpl configuration = ServerConfigurationImpl.create();  
    private final NetworkManager networkManager;
    private final Thread shutdownThread = createShutdownThread();

    public MinecraftServerImpl() {
        this.networkManager = new NetworkManager(this);

        try {
            Runtime.getRuntime().addShutdownHook(this.shutdownThread);
            this.networkManager.bind();
        } catch (Throwable t) {
            LOGGER.error("an error occurred while starting the server", t);
            this.shutdown();
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
    public ServerConfigurationImpl configuration() {
        return this.configuration;
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
                this.networkManager.shutdown(); 
                LOGGER.info("Successfully shut down the server");    
            });
    }
}
