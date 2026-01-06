package xyz.endelith.server.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.simpleyaml.configuration.file.YamlFile;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.server.network.netty.transport.NettyTransportSelector;

public final class ServerConfigurationImpl implements ServerConfiguration {

    private static final String FILE_NAME = "server.yml";
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final YamlFile config;
    private final Path file;

    private Component motd;
    private Component unsupportedVersionMessage;
    private Component transferNotAllowedMessage;

    private NettyTransportSelector transport;

    public ServerConfigurationImpl() {
        this.file = Path.of(FILE_NAME);
        this.config = new YamlFile(file.toFile());
    }

    public void load() throws IOException {
        if (!Files.exists(file)) {
            this.config.createNewFile();
        }

        config.load();
        writeDefaults();
        loadComponents();
        loadTransport();
        config.save();
    }

    private void writeDefaults() {
        for (Key key : Key.values()) {
            this.config.setComment(key.path, key.comment);

            if (!this.config.contains(key.path)) {
                this.config.set(key.path, key.defaultValue);
            }
        }
    }

    private void loadComponents() {
        this.motd = this.deserialize(Key.MOTD);
        this.unsupportedVersionMessage = this.deserialize(Key.UNSUPPORTED_VERSION_MESSAGE);
        this.transferNotAllowedMessage = this.deserialize(Key.TRANSFER_NOT_ALLOWED_MESSAGE);
    }

    private void loadTransport() {
        String value = config.getString(Key.TRANSPORT.path, "auto");
    
        try {
            transport = NettyTransportSelector.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            transport = NettyTransportSelector.AUTO;
        }
    }

    private Component deserialize(Key key) {
        String value = this.config.getString(key.path);
        return MINI_MESSAGE.deserialize(value == null ? "" : value);
    }

    @Override
    public String serverAddress() {
        return this.config.getString(Key.ADDRESS.path);
    }

    @Override
    public int serverPort() {
        return this.config.getInt(Key.PORT.path);
    }

    @Override
    public boolean onlineMode() {
        return this.config.getBoolean(Key.ONLINE_MODE.path);
    }

    @Override
    public int maxPlayers() {
        return this.config.getInt(Key.MAX_PLAYERS.path);
    }

    @Override
    public Component serverListDescription() {
        return this.motd;
    }

    @Override
    public Component unsupportedVersionMessage() {
        return this.unsupportedVersionMessage;
    }

    @Override
    public boolean enforceSecureChat() {
        return this.config.getBoolean(Key.ENFORCE_SECURE_CHAT.path);
    }

    @Override
    public boolean allowTransferPacket() {
        return this.config.getBoolean(Key.ALLOW_TRANSFER_PACKET.path);
    }

    @Override
    public Component transferNotAllowedMessage() {
        return this.transferNotAllowedMessage;
    }

    public NettyTransportSelector transportSelector() {
        return transport;
    }

    private enum Key {

        ADDRESS(
            "address",
            "0.0.0.0",
            """
            Network address the server will bind to
            0.0.0.0 means all available network interfaces
            """
        ),

        PORT(
            "port",
            25565,
            """
            TCP port used for incoming connections
            """
        ),

        ONLINE_MODE(
            "online-mode",
            true,
            """
            Controls whether the server verifies clients with Mojang
            Disabling this removes account authentication and UUID validation
            """
        ),

        MAX_PLAYERS(
            "max-players",
            100,
            """
            Maximum number of players that can be connected at the same time
            """
        ),

        MOTD(
            "motd",
            "<green>A Endelith server</green>",
            """
            Text displayed in the multiplayer server list
            Visible during status ping
            """
        ),

        UNSUPPORTED_VERSION_MESSAGE(
            "unsupported-version-message",
            "<red>Your client version is not supported.</red>",
            """
            Message sent to clients using an incompatible protocol version
            """
        ),

        ENFORCE_SECURE_CHAT(
            "enforce-secure-chat",
            false,
            """
            Forces clients to use signed (secure) chat messages
            """
        ),

        ALLOW_TRANSFER_PACKET(
            "allow-transfer-packet",
            false,
            """
            Allows clients connecting via a Transfer packet from another server.
            If disabled, such connections are rejected during login.
            """
        ),

        TRANSFER_NOT_ALLOWED_MESSAGE(
            "transfer-not-allowed-message",
            "<red>Transfers are not allowed on this server.</red>",
            """
            Message sent when a client attempts to connect via Transfer packet
            while transfers are disabled
            """
        ),
        
        TRANSPORT(
            "transport",
            "auto",
            """
            Network transport backend used by Netty
            Possible values:
              - auto   (recommended)
              - nio
              - epoll  (linux only)
              - kqueue (macos and bsd only)
            """
        );

        final String path;
        final Object defaultValue;
        final String comment;

        Key(String path, Object defaultValue, String comment) {
            this.path = Objects.requireNonNull(path, "path");
            this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue");
            this.comment = comment.strip();
        }
    }
}
