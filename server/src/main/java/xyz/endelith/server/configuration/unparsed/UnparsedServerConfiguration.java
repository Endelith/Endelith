package xyz.endelith.server.configuration.unparsed;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Comments;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import java.nio.file.Path;
import xyz.endelith.server.configuration.ConfigurationPlaceholder;
import xyz.endelith.server.network.netty.transport.NettyTransportSelector;

@Header("Endelith Server Configuration")
public final class UnparsedServerConfiguration extends OkaeriConfig {

    @Comment(
        "The network address the server will bind to."
    )
    private String address = "0.0.0.0";

    @Comment(
        "The TCP port on which the server will accept incoming connections."
    )
    private int port = 25565;

    @Comments({
        @Comment("Specifies the Netty transport implementation to be used."),
        @Comment(
            "AUTO   - Automatically selects the optimal transport for the current platform."
        ),
        @Comment(
            "NIO    - Java NIO-based transport (portable fallback)."
        ),
        @Comment(
            "EPOLL  - Linux native epoll transport (lower latency)."
        ),
        @Comment(
            "KQUEUE - macOS native kqueue transport."
        )
    })
    private NettyTransportSelector transport = NettyTransportSelector.AUTO;

    @Comment(
        "A message used during disconnection when a player is trying to join "
            + "with an unsupported version."
    )
    @CustomKey("unsupported-version-message")
    private String unsupportedVersionMessage =
        createDefaultUnsupportedVersionMessage();

    @CustomKey("maximum-players")
    @Comments({
        @Comment(
            "The maximum number of players that may be reported as online."
        ),
        @Comment(
            "By default, this value is primarily used for server list ping responses."
        ),
        @Comment(
            "Plugins may additionally use this value to enforce hard player limits."
        )
    })
    private int maximumPlayers = 100;

    @CustomKey("transfers-allowed")
    @Comment(
        "Controls whether players joining via server transfer are permitted."
    )
    private boolean transfersAllowed = false;

    @CustomKey("transfers-not-allowed-message")
    @Comment(
        "The disconnect message shown when a player attempts to join via transfer "
            + "while transfers are disabled."
    )
    private String transfersNotAllowedMessage =
        "<red>Transfers are not allowed on this server!</red>";

    @CustomKey("server-list-description")
    @Comment(
        "The description displayed in the Minecraft server list."
    )
    private String serverListDescription =
        "<dark_green>An Endelith server</dark_green>";

    @CustomKey("online-mode")
    @Comment(
        "Whether the server should verify players using Mojang's authentication servers."
    )
    private boolean onlineMode = true;

    @Comment(
        "A packet length, since which packets are compressed."
    )
    @CustomKey("compression-threshold")
    private int compressionThreshold = 256;

    public String address() {
        return this.address;
    }

    public int port() {
        return this.port;
    }

    public NettyTransportSelector transport() {
        return this.transport;
    }

    public String unsupportedVersionMessage() {
        return this.unsupportedVersionMessage;
    }

    public int maximumPlayers() {
        return this.maximumPlayers;
    }

    public boolean transfersAllowed() {
        return this.transfersAllowed;
    }

    public String transfersNotAllowedMessage() {
        return this.transfersNotAllowedMessage;
    }

    public String serverListDescription() {
        return this.serverListDescription;
    }

    public boolean onlineMode() {
        return this.onlineMode;
    }

    public int compressionThreshold() {
        return this.compressionThreshold;
    }

    public static UnparsedServerConfiguration create() {
        return ConfigManager.create(UnparsedServerConfiguration.class, it -> {
            it.configure(opt -> {
                opt.bindFile(Path.of("server.yml"));
                opt.configurer(
                    new YamlSnakeYamlConfigurer(),
                    new SerdesCommons()
                );
                opt.removeOrphans(true);
            });

            it.saveDefaults();
            it.load(true);
        });
    }

    private static String createDefaultUnsupportedVersionMessage() {
        return String.format(
            "<dark_red><bold>Unsupported version! Please use <%s>.</bold></dark_red>",
            ConfigurationPlaceholder.MINECRAFT_VERSION_NAME.placeholderName()
        );
    }
}
