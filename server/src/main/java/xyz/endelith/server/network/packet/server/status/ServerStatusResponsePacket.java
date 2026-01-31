package xyz.endelith.server.network.packet.server.status;

import org.jspecify.annotations.Nullable;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.codec.JsonComponentCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;
import xyz.endelith.util.ping.ServerListPing;
import xyz.endelith.util.ping.ServerListPing.Favicon;
import xyz.endelith.util.ping.ServerListPing.Players;
import xyz.endelith.util.ping.ServerListPing.Players.Sample;
import xyz.endelith.util.ping.ServerListPing.Version;

public record ServerStatusResponsePacket(String json) implements ServerPacket { 
    
    public static final StreamCodec<ServerStatusResponsePacket> STREAM_CODEC = StreamCodec.of(
        StreamCodec.STRING, ServerStatusResponsePacket::json,
        ServerStatusResponsePacket::new
    );

    private static final Codec<@Nullable Favicon> FAVICON_CODEC =
        Codec.STRING.transform(Favicon::new, Favicon::image);

    private static final Codec<Version> VERSION_CODEC = StructCodec.of(
        "name", Codec.STRING, Version::name,
        "protocol", Codec.INT, Version::protocolVersion,
        Version::new
    );

    private static final Codec<Sample> SAMPLE_CODEC = StructCodec.of(
        "name", Codec.STRING, Sample::name,
        "id", Codec.UUID, Sample::uuid,
        Sample::new
    );
 
    private static final Codec<Players> PLAYERS_CODEC = StructCodec.of(
        "max", Codec.INT, Players::maximumPlayers,
        "online", Codec.INT, Players::onlinePlayers,
        "sample", SAMPLE_CODEC.list(), Players::samples, 
        Players::new
    ); 

    public static final Codec<ServerListPing> SERVER_LIST_PING_CODEC = StructCodec.of(
        "version", VERSION_CODEC, ServerListPing::version,
        "players", PLAYERS_CODEC, ServerListPing::players,
        "description", JsonComponentCodec.STRING_CODEC, ServerListPing::description,
        "favicon", FAVICON_CODEC.optional(), ServerListPing::favicon,
        "enforcesSecureChat", Codec.BOOLEAN, ServerListPing::enforcesSecureChat,
        ServerListPing::new
    );
}
