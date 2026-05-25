package xyz.endelith.server.configuration.unparsed;

import java.nio.file.Path;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import xyz.endelith.server.network.netty.transport.NettyTransportSelector;

@ConfigSerializable
public final class UnparsedServerConfiguration {

    private static final Path CONFIGURATION_PATH = Path.of("server.yml");
    private static final String CONFIGURATION_HEADER = "Endelith Server Configuration";

    @Comment("The network address the server will bind to.")
    private String address = "0.0.0.0";

    @Comment("The TCP port on which the server will accept incoming connections.")
    private int port = 25565;

    @Comment("""
        Specifies the Netty transport implementation to be used.
        AUTO   - Automatically selects the optimal transport for the current platform.
        NIO    - Java NIO-based transport (portable fallback).
        EPOLL  - Linux native epoll transport (lower latency).
        KQUEUE - macOS native kqueue transport.""")
    private NettyTransportSelector selector = NettyTransportSelector.AUTO;

    @Comment("""
            The maximum number of players that may be reported as online.
            By default, this value is primarily used for server list ping responses.
            Plugins may additionally use this value to enforce hard player limits.""")
    private int maximumPlayers = 100;

    @Comment("The description displayed in the Minecraft server list.")
    private String serverListDescription = "<dark_green>An Endelith server</dark_green>";

    public String address() {
        return this.address;
    }

    public int port() {
        return this.port;
    }

    public int maximumPlayers() {
        return this.maximumPlayers;
    }

    public String serverListDescription() {
        return this.serverListDescription;
    }

    public NettyTransportSelector selector() {
        return this.selector;
    }

    public static UnparsedServerConfiguration create() {
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(CONFIGURATION_PATH)
                .commentsEnabled(true)
                .defaultOptions(options -> options.header(CONFIGURATION_HEADER).shouldCopyDefaults(true))
                .build();

        try {
            CommentedConfigurationNode node = loader.load();
            UnparsedServerConfiguration configuration = node.get(UnparsedServerConfiguration.class);
            loader.save(node.set(UnparsedServerConfiguration.class, configuration));
            return configuration;
        } catch (ConfigurateException ex) {
            throw new IllegalStateException("Failed to load server configuration", ex);
        }
    }
}
