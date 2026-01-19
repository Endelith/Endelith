package xyz.endelith.server.network.packet.client.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.PlayerConnectionImpl;
import xyz.endelith.server.network.packet.client.ClientPacket;

public record ClientLoginPluginResponsePacket(int messageId, byte[] data) implements ClientPacket {

    public static final StreamCodec<ClientLoginPluginResponsePacket> SERIALIZER = StreamCodec.of(
        StreamCodec.VAR_INT, ClientLoginPluginResponsePacket::messageId,
        StreamCodec.BYTE_ARRAY.optional(), ClientLoginPluginResponsePacket::data,
        ClientLoginPluginResponsePacket::new
    );

    @Override
    public void handle(PlayerConnectionImpl connection) {
        connection.loginPacketHandler().handle(this);
    }
}
