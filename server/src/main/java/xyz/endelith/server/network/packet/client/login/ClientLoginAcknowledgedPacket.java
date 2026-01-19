package xyz.endelith.server.network.packet.client.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientLoginAcknowledgedPacket() implements ClientPacket {

    public static final StreamCodec<ClientLoginAcknowledgedPacket> SERIALIZER = StreamCodec.of(
        ClientLoginAcknowledgedPacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.loginPacketHandler().handle(this);
    }
}
