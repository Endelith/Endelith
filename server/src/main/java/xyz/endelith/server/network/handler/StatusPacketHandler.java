package xyz.endelith.server.network.handler;

import java.util.List;
import xyz.endelith.cosine.transcoder.JsonTranscoder;
import xyz.endelith.event.ping.ServerListPingEvent;
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

    public void handle(ClientStatusRequestPacket packet) {
        ServerConfigurationImpl configuration = server.configuration();

        // TODO: online players and samples?
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
            false // TODO: enforce secure chat?
        );

        ServerListPingEvent event = new ServerListPingEvent(connection, baseStatus);
        
        if (!event.isCanceled()) {
            this.server.eventManager().call(event);

            ServerListPing finalStatus = event.getServerListPing();
    
            String json = ServerStatusResponsePacket.SERVER_LIST_PING_CODEC
                .encode(JsonTranscoder.INSTANCE, finalStatus)
                .toString();
    
            this.connection.sendPacket(new ServerStatusResponsePacket(json)); 
        }
    }

    public void handle(ClientStatusPingRequestPacket packet) {
        this.connection.sendPacket(new ServerStatusPongResponsePacket(packet.timestamp())); 
    }
}
