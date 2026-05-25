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
        boolean transfersAllowed,
        Component transfersNotAllowedMessage,
        Component unsupportedVersionMessage,
        Component serverListDescription,
        NettyTransportSelector selector
) implements ServerConfiguration {

    public ServerConfigurationImpl {
        Objects.requireNonNull(address, "address");
        Objects.requireNonNull(transfersNotAllowedMessage, "transfers not allowed message");
        Objects.requireNonNull(unsupportedVersionMessage, "unsupported version message");
        Objects.requireNonNull(serverListDescription, "server list description");
        Objects.requireNonNull(selector, "selector");
    }

    public static ServerConfigurationImpl create() {
        UnparsedServerConfiguration unparsed = UnparsedServerConfiguration.create();
        TagResolver[] tagResolvers = ConfigurationPlaceholder.createTagResolvers();
        return new ServerConfigurationImpl(
                unparsed.address(),
                unparsed.port(),
                unparsed.maximumPlayers(),
                unparsed.transfersAllowed(),
                deserialize(unparsed.transfersNotAllowedMessage(), tagResolvers),
                deserialize(unparsed.unsupportedVersionMessage(), tagResolvers),
                deserialize(unparsed.serverListDescription(), tagResolvers),
                unparsed.selector()
        );
    }

    private static Component deserialize(String serialized, TagResolver[] tagResolvers) {
        return MiniMessage.miniMessage().deserialize(serialized, tagResolvers);
    }
}
