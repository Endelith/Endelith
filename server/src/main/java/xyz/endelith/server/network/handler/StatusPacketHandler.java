package xyz.endelith.server.network.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.endelith.configuration.ServerConfiguration;
import xyz.endelith.cosine.transcoder.JsonTranscoder;
import xyz.endelith.event.events.server.ServerListPingEvent;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.status.ClientStatusPingRequestPacket;
import xyz.endelith.server.network.packet.client.status.ClientStatusRequestPacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusPongResponsePacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusResponsePacket;
import xyz.endelith.util.ping.ServerListPing;

public final class StatusPacketHandler extends PacketHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusPacketHandler.class);

    public StatusPacketHandler(PlayerConnectionImpl connection) {
        super(connection);
    }

    public void handle(ClientStatusRequestPacket packet) {
        ServerConfiguration configuration = server.configuration();

        //TODO: online players and samples?
        ServerListPing baseStatus = new ServerListPing(
            new ServerListPing.Version(
                server.minecraftVersion(),
                server.protocolVersion()
            ),
            new ServerListPing.Players(
                configuration.maximumPlayers(),
                1,
                List.of()
            ),
            configuration.serverListDescription(),
            null,
            false //TODO: enforce secure chat?
        );

        ServerListPingEvent event = new ServerListPingEvent(connection, baseStatus);

        try {
            server.eventManager().call(event);
        } catch (Throwable t) {
            LOGGER.error("An error occurred during handling of the server list ping event", t);
        }

        if (event.isCancelled()) {
            return;
        }

        ServerListPing finalStatus = event.getPing();

        String json = ServerStatusResponsePacket.SERVER_LIST_PING_CODEC
            .encode(JsonTranscoder.INSTANCE, finalStatus)
            .toString();

        connection.sendPacket(new ServerStatusResponsePacket(json)); 
    }

    public void handle(ClientStatusPingRequestPacket packet) {
        connection.sendPacket(new ServerStatusPongResponsePacket(packet.timestamp())); 
    }
}
