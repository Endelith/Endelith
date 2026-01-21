package xyz.endelith.server.network.packet.server.configuration;

import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerConfigurationFinishPacket() implements ServerPacket {

    public static final StreamCodec<ServerConfigurationFinishPacket> SERIALIZER = StreamCodec.of(
        ServerConfigurationFinishPacket::new
    );
}
