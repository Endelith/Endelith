package xyz.endelith.server.network.packet.client.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientLoginEncryptionResponsePacket(byte[] sharedKey, byte[] verifytoken) implements ClientPacket {

    public static final StreamCodec<ClientLoginEncryptionResponsePacket>SERIALIZER = StreamCodec.of(
        StreamCodec.BYTE_ARRAY, ClientLoginEncryptionResponsePacket::sharedKey,
        StreamCodec.BYTE_ARRAY, ClientLoginEncryptionResponsePacket::verifytoken,
        ClientLoginEncryptionResponsePacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.loginPacketHandler().handle(this);
    } 
}
