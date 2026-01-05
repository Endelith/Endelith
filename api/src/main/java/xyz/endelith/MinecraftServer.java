package xyz.endelith;

public interface MinecraftServer {
    String brandName();
    String minecraftVersion();
    int protocolVersion();
    void shutdown();
}
