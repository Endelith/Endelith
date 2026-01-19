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

    public ClientLoginStartPacket {
        if (username.length() > 16)
            throw new IllegalArgumentException("Username is not allowed to be longer than 16 characters");
    }

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.loginPacketHandler().handle(this);
    }
}
