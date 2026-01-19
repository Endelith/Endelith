package xyz.endelith.server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.endelith.ApiVersion;
import xyz.endelith.MinecraftServer;
import xyz.endelith.event.EventManager;
import xyz.endelith.server.configuration.ServerConfigurationImpl;
import xyz.endelith.server.console.EndelithConsole;
import xyz.endelith.server.network.NetworkManager;

public final class MinecraftServerImpl implements MinecraftServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinecraftServerImpl.class);
    
    private static final String BRAND_NAME = "Endelith";
    
    private final ServerConfigurationImpl configuration = ServerConfigurationImpl.create(); 
    private final EndelithConsole console;
    private final NetworkManager networkManager;
    private final EventManager eventManager;

    private final Thread shutdownThread = createShutdownThread();

    private KeyPair keyPair;

    public MinecraftServerImpl() {
        this.console = new EndelithConsole(this);
        this.eventManager = new EventManager();
        this.networkManager = new NetworkManager(this);
 
        try {
            Runtime.getRuntime().addShutdownHook(shutdownThread);
          
            if (configuration.onlineMode()) {
                LOGGER.info("Generating RSA key pair for authentication");
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
                generator.initialize(1024);
                this.keyPair = generator.generateKeyPair();
            }

            networkManager.bind();
            console.start();
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
        return ApiVersion.LATEST.name(); 
    }

    @Override
    public int protocolVersion() {
        return ApiVersion.LATEST.protocolVersion();
    }

    @Override
    public ServerConfigurationImpl configuration() {
        return configuration;
    }

    @Override
    public EventManager eventManager() {
        return eventManager;
    }

    @Override
    public void shutdown() {
        try {
            shutdownThread.start();
        } catch (IllegalThreadStateException exception) {
            // The shutdown has already been scheduled
        }
    }

    public KeyPair keyPair() {
        return keyPair;
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
