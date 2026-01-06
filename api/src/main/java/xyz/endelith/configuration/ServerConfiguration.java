package xyz.endelith.configuration;

import net.kyori.adventure.text.Component;

public interface ServerConfiguration {
    String serverAddress();
    int serverPort();
    boolean onlineMode();
    int maxPlayers();
    Component serverListDescription();
    Component unsupportedVersionMessage();
    boolean enforceSecureChat();
    boolean allowTransferPacket();
    Component transferNotAllowedMessage();
}
