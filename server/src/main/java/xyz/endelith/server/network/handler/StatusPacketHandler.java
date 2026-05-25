package xyz.endelith.server.network.handler;

import java.util.List;
import xyz.endelith.cosine.transcoder.JsonTranscoder;
import xyz.endelith.event.Events;
import xyz.endelith.event.server.ServerListPingEvent;
import xyz.endelith.server.MinecraftServerImpl;
import xyz.endelith.server.configuration.ServerConfigurationImpl;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.status.ClientStatusPingRequestPacket;
import xyz.endelith.server.network.packet.client.status.ClientStatusRequestPacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusPongResponsePacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusResponsePacket;
import xyz.endelith.util.ping.ServerListPing;

public final class StatusPacketHandler extends PacketHandler {

    public StatusPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientStatusPingRequestPacket packet) {
        this.connection.sendPacket(new ServerStatusPongResponsePacket(packet.timestamp()));
    }

    public void handle(ClientStatusRequestPacket packet) {
        ServerConfigurationImpl configuration = this.server.configuration();

        ServerListPing status = ServerListPing.builder()
                .version(new ServerListPing.Version(
                        MinecraftServerImpl.MINECRAFT_VERSION,
                        MinecraftServerImpl.PROTOCOL_VERSION
                ))
                .players(new ServerListPing.Players(
                        configuration.maximumPlayers(),
                        0,
                        List.of()
                ))
                .description(configuration.serverListDescription())
                .enforcesSecureChat(false)
                .build();

        ServerListPingEvent event = new ServerListPingEvent(this.connection, status);
        this.server.pluginEventManager().fire(Events.SERVER_LIST_PING, event);

        if (event.isCancelled()) {
            return;
        }

        String json = ServerStatusResponsePacket.SERVER_LIST_PING_CODEC
                .encode(JsonTranscoder.INSTANCE, event.status())
                .toString();

        this.connection.sendPacket(new ServerStatusResponsePacket(json));
    }
}
