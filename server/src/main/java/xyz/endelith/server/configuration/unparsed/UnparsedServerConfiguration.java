package xyz.endelith.server.configuration.unparsed;

import java.nio.file.Path;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Comments;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import xyz.endelith.server.network.netty.transport.NettyTransportSelector;

@Header("Endelith configuration file")
public final class UnparsedServerConfiguration extends OkaeriConfig {

    @Comment("The network address the server will bind to")
    private String address = "0.0.0.0";

    @Comment("The TCP port the server will listen on")
    private int port = 25565;

    @Comments({
        @Comment("Netty transport implementation selector"),
        @Comment("AUTO   - Automatically selects the best transport"),
        @Comment("NIO    - Java NIO (default fallback)"),
        @Comment("EPOLL  - Linux native epoll (lower latency)"),
        @Comment("KQUEUE - macOS native kqueue")
    })
    private NettyTransportSelector transport = NettyTransportSelector.AUTO;

    @CustomKey("maximum-players")
    @Comment("Maximum number of players allowed on the server")
    private int maximumPlayers = 100;

    @CustomKey("server-list-description")
    @Comment("Description shown in the Minecraft server list")
    private String serverListDescription = "<dark_green>A Endelith server</dark_green>";

    public String address() {
        return address;
    }

    public int port() {
        return port;
    }

    public NettyTransportSelector transport() {
        return transport;
    }

    public int maximumPlayers() {
        return maximumPlayers;
    }

    public String serverListDescription() {
        return serverListDescription;
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
}
