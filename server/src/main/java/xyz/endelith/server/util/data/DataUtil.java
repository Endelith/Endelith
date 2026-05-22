package xyz.endelith.server.util.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.cosine.transcoder.JsonTranscoder;
import xyz.endelith.cosine.transcoder.NbtTranscoder;
import xyz.endelith.cosine.transcoder.Transcoder;
import xyz.endelith.registry.feature.KnownPack;
import xyz.endelith.server.registry.MinecraftRegistryImpl.RegistrationInfo;

public final class DataUtil {

    private DataUtil() {
    }

    private static final Codec<List<Key>> ENTRY_TAGS_CODEC = Codec.KEY.list()
            .optional()
            .defaultValue(List.of());

    private static final Codec<KnownPack> KNOWN_PACK_CODEC = StructCodec.of(
            "namespace", Codec.STRING, KnownPack::namespace,
            "path", Codec.STRING, KnownPack::path,
            "version", Codec.STRING, KnownPack::version,
            KnownPack::new
    );

    public static <D, V> LoadResult<V> loadEntries(
            String resourcePath,
            Codec<D> dataCodec,
            Function<D, V> valueConverter
    ) {
        try (InputStream in = DataUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalStateException("Resource not found: " + resourcePath);
            }

            List<RegistrationInfo<V>> infos = new ArrayList<>();
            Multimap<Key, Key> tags = HashMultimap.create();

            JsonElement root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            for (JsonElement element : root.getAsJsonArray()) {
                Transcoder.VirtualMap<JsonElement> map = JsonTranscoder.INSTANCE.decodeMap(element);
                Key key = Codec.KEY.decode(JsonTranscoder.INSTANCE, map.getValue("key"));
                KnownPack pack = KNOWN_PACK_CODEC.optional()
                        .decode(JsonTranscoder.INSTANCE, map.getValue("known_pack"));
                JsonElement valueElement = map.getValue("value");
                D data;
                if (valueElement.isJsonPrimitive() && valueElement.getAsJsonPrimitive().isString()) {
                    BinaryTag nbt = TagStringIO.tagStringIO().asCompound(valueElement.getAsString());
                    data = dataCodec.decode(NbtTranscoder.INSTANCE, nbt);
                } else {
                    data = dataCodec.decode(JsonTranscoder.INSTANCE, valueElement);
                }
                infos.add(new RegistrationInfo<>(key, valueConverter.apply(data), pack));
                ENTRY_TAGS_CODEC.decode(JsonTranscoder.INSTANCE, map.getValue("tags"))
                        .forEach(tagKey -> tags.put(tagKey, key));
            }

            return new LoadResult<>(infos, tags);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load registry: " + resourcePath, ex);
        }
    }

    public record LoadResult<V>(List<RegistrationInfo<V>> registrations, Multimap<Key, Key> tags) {
        public LoadResult {
            Objects.requireNonNull(registrations, "registrations");
            Objects.requireNonNull(tags, "tags");
        }
    }
}
