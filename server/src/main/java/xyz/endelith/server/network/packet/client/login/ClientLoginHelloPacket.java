package xyz.endelith.server.network.packet.client.login;

import java.util.UUID;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientLoginHelloPacket(String username, UUID uuid) implements ClientPacket {

    public static final StreamCodec<ClientLoginHelloPacket> STREAM_CODEC = StreamCodec.of(
            StreamCodec.STRING, ClientLoginHelloPacket::username,
            StreamCodec.UUID, ClientLoginHelloPacket::uuid,
            ClientLoginHelloPacket::new
    );

    public ClientLoginHelloPacket {
        if (username.length() > 16) {
            throw new IllegalArgumentException(String.format(
                "Username too long: %s",
                username.length()
            ));
        }
    }

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.loginPacketHandler().handle(this);
    }
}
