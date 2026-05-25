package xyz.endelith.server.configuration;

import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.server.configuration.unparsed.UnparsedServerConfiguration;
import xyz.endelith.server.network.netty.transport.NettyTransportSelector;

public record ServerConfigurationImpl(
        String address,
        int port,
        int maximumPlayers,
        Component serverListDescription,
        NettyTransportSelector selector
) implements ServerConfiguration {

    public ServerConfigurationImpl {
        Objects.requireNonNull(address, "address");
        Objects.requireNonNull(serverListDescription, "server list description");
    }

    public static ServerConfigurationImpl create() {
        UnparsedServerConfiguration unparsed = UnparsedServerConfiguration.create();
        TagResolver[] tagResolvers = ConfigurationPlaceholder.createTagResolvers();
        return new ServerConfigurationImpl(
                unparsed.address(),
                unparsed.port(),
                unparsed.maximumPlayers(),
                deserialize(unparsed.serverListDescription(), tagResolvers),
                unparsed.selector()
        );
    }

    private static Component deserialize(String serialized, TagResolver[] tagResolvers) {
        return MiniMessage.miniMessage().deserialize(serialized, tagResolvers);
    }
}
