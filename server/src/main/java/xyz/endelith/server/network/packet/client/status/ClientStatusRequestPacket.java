package xyz.endelith.server.network.packet.client.status;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientStatusRequestPacket() implements ClientPacket {
    
    public static final StreamCodec<ClientStatusRequestPacket> SERIALIZER = StreamCodec.of(
        ClientStatusRequestPacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.statusPacketHandler().handle(this); 
    }
}
