package xyz.endelith.server.network.packet.server.login;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerLoginEncryptionRequestPacket(
    String serverId, 
    byte[] publicKey,
    byte[] verifyToken, 
    boolean authenticate
) implements ServerPacket {
    
    public static final StreamCodec<ServerLoginEncryptionRequestPacket> SERIALIZER = StreamCodec.of(
        StreamCodec.STRING, ServerLoginEncryptionRequestPacket::serverId,
        StreamCodec.BYTE_ARRAY, ServerLoginEncryptionRequestPacket::publicKey,
        StreamCodec.BYTE_ARRAY, ServerLoginEncryptionRequestPacket::verifyToken,
        StreamCodec.BOOLEAN, ServerLoginEncryptionRequestPacket::authenticate,
        ServerLoginEncryptionRequestPacket::new
    );
}
