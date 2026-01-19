package xyz.endelith.server.network.packet.client.login;

import java.util.UUID;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientLoginStartPacket(String username, UUID uuid) implements ClientPacket {

    public static final StreamCodec<ClientLoginStartPacket> SERIALIZER = StreamCodec.of(
        StreamCodec.STRING, ClientLoginStartPacket::username,
        StreamCodec.UUID, ClientLoginStartPacket::uuid,
        ClientLoginStartPacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.loginPacketHandler().handle(this);
    }
}
