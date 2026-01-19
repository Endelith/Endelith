package xyz.endelith.server.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import xyz.endelith.cosine.transcoder.JsonTranscoder;
import xyz.endelith.server.codec.profile.GameProfileCodec;
import xyz.endelith.util.profile.GameProfile;

public final class MojangUtil {

    private static final String SESSION_SERVER_URL = "https://sessionserver.mojang.com";
    private static final String HAS_JOINED_URL = String.format("%s/session/minecraft/hasJoined?username=%%s&serverId=%%s", SESSION_SERVER_URL);
  
    private MojangUtil() {}

    public static GameProfile hasJoined(String username, String serverId) {
        JsonObject response = apiRequest(String.format(HAS_JOINED_URL, username, serverId));
 
        if (response == null) {
            throw new IllegalStateException(
                "Mojang session server returned an empty or invalid response"
            );
        }

        return GameProfileCodec.STRING_CODEC.decode(JsonTranscoder.INSTANCE, response);
    }

    public static UUID fromMojang(String uniqueId) {
        long lo = 0;
        long hi = 0;

        for (int i = 0, j = 0; i < 32; ++j) {
            int current = 0;
            char c = uniqueId.charAt(i);

            if (c >= '0' && c <= '9') {
                current = c - '0';
            } else if (c >= 'a' && c <= 'f') {
                current = c - 'a' + 10;
            } else if (c >= 'A' && c <= 'F') {
                current = c - 'A' + 10;
            } else {
                throw nonHexCharacter(i, c);
            }

            current <<= 4;
            c = uniqueId.charAt(++i);

            if (c >= '0' && c <= '9') {
                current |= c - '0';
            } else if (c >= 'a' && c <= 'f') {
                current |= c - 'a' + 10;
            } else if (c >= 'A' && c <= 'F') {
                current |= c - 'A' + 10;
            } else {
                throw nonHexCharacter(i, c);
            }

            if (j < 8) {
                hi = (hi << 8) | current;
            } else {
                lo = (lo << 8) | current;
            }

            ++i;
        }

        return new UUID(hi, lo);
    }

    private static NumberFormatException nonHexCharacter(int index, char character) {
        return new NumberFormatException(
                String.format(
                        "Invalid hexadecimal character at index %d: '%c' (0x%s)",
                        index,
                        character,
                        Integer.toHexString(character)
                )
        );
    }

    private static JsonObject apiRequest(String url) {
        try (InputStreamReader reader = new InputStreamReader(
                URI.create(url).toURL().openStream()
        )) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new IllegalStateException(
                String.format("Failed to perform Mojang API request: %s", url), e
            );
        } catch (IllegalStateException e) {
            throw new IllegalStateException(
                "Invalid JSON response received from Mojang API", e
            );
        }
    }
}
