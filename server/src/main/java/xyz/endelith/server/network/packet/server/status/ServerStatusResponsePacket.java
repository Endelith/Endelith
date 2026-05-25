package xyz.endelith.server.network.packet.server.status;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;
import xyz.endelith.util.ping.ServerListPing;

public record ServerStatusResponsePacket(String json) implements ServerPacket {

    public static final StreamCodec<ServerStatusResponsePacket> STREAM_CODEC = StreamCodec.of(
            StreamCodec.STRING, ServerStatusResponsePacket::json,
            ServerStatusResponsePacket::new
    );

    private static final Codec<ServerListPing.Favicon> FAVICON_CODEC =
            Codec.STRING.transform(ServerListPing.Favicon::new, ServerListPing.Favicon::image);

    private static final Codec<ServerListPing.Version> VERSION_CODEC = StructCodec.of(
            "name", Codec.STRING, ServerListPing.Version::name,
            "protocol", Codec.INT, ServerListPing.Version::protocolVersion,
            ServerListPing.Version::new
    );

    private static final Codec<ServerListPing.Players.Sample> SAMPLE_CODEC = StructCodec.of(
            "name", Codec.STRING, ServerListPing.Players.Sample::name,
            "id", Codec.UUID, ServerListPing.Players.Sample::uuid,
            ServerListPing.Players.Sample::new
    );

    private static final Codec<ServerListPing.Players> PLAYERS_CODEC = StructCodec.of(
            "max", Codec.INT, ServerListPing.Players::maximumPlayers,
            "online", Codec.INT, ServerListPing.Players::onlinePlayers,
            "sample", SAMPLE_CODEC.list(), ServerListPing.Players::samples,
            ServerListPing.Players::new
    );

    public static final Codec<ServerListPing> SERVER_LIST_PING_CODEC = StructCodec.of(
            "version", VERSION_CODEC, ServerListPing::version,
            "players", PLAYERS_CODEC, ServerListPing::players,
            "description", Codec.JSON_COMPONENT, ServerListPing::description,
            "favicon", FAVICON_CODEC.optional(), ServerListPing::favicon,
            "enforcesSecureChat", Codec.BOOLEAN, ServerListPing::enforcesSecureChat,
            ServerListPing::new
    );
}
