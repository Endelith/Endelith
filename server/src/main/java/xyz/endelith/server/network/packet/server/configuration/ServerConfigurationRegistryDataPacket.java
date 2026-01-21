package xyz.endelith.server.network.packet.server.configuration;

import java.util.List;

import edu.umd.cs.findbugs.annotations.Nullable;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.codec.key.KeyCodec;
import xyz.endelith.server.codec.misc.BinaryTagCodec;
import xyz.endelith.server.network.packet.server.ServerPacket;

public record ServerConfigurationRegistryDataPacket(Key registry, List<Entry> entries) implements ServerPacket {

    public static final StreamCodec<ServerConfigurationRegistryDataPacket> SERIALIZER = StreamCodec.of(
        KeyCodec.STREAM_CODEC, ServerConfigurationRegistryDataPacket::registry,
        Entry.STREAM_CODEC.list(), ServerConfigurationRegistryDataPacket::entries,
        ServerConfigurationRegistryDataPacket::new
    );

    public record Entry(Key key, @Nullable BinaryTag data) {
        public static final StreamCodec<Entry> STREAM_CODEC = StreamCodec.of(
            KeyCodec.STREAM_CODEC, Entry::key,
            BinaryTagCodec.STREAM_CODEC.optional(), Entry::data,
            Entry::new
        );
    }
}
