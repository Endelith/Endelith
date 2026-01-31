package xyz.endelith.server.network.packet.client.status;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientStatusPingRequestPacket(Long timestamp) implements ClientPacket {
    
    public static final StreamCodec<ClientStatusPingRequestPacket> STREAM_CODEC = StreamCodec.of(
        StreamCodec.LONG, ClientStatusPingRequestPacket::timestamp,
        ClientStatusPingRequestPacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.statusPacketHandler().handle(this);
    }
}
