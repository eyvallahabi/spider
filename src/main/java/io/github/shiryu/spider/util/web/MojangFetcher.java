package io.github.shiryu.spider.util.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class MojangFetcher {

    private final Map<UUID, UUIDPair> CACHE = new HashMap<>();

    @NotNull
    public UUIDPair lookup(@NotNull final String name) throws IOException, NullPointerException {
        if(getByName(name) != null)
            return getByName(name);

        final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
        final URLConnection conn = url.openConnection();

        conn.setDoOutput(true);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        final JsonObject root = new JsonParser().parse(reader.readLine()).getAsJsonObject();
        final UUID uuid = UUID.fromString(root.get("id").getAsString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
        final String playerName = root.get("name").getAsString();

        reader.close();

        final UUIDPair pair = new UUIDPair(uuid, playerName);

        CACHE.put(uuid, pair);

        return pair;
    }

    @NotNull
    public UUIDPair lookup(@NotNull final UUID uuid){
        if (getByUUID(uuid) != null)
            return getByUUID(uuid);

        try{
            final URL url = new URL(String.format("https://api.mojang.com/user/profiles/%s/names", uuid.toString().replace("-", "")));

            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() == 400)
                return UUIDPair.empty();

            final InputStream input = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            final JsonElement element = new JsonParser().parse(reader);
            final JsonArray array = element.getAsJsonArray();
            final JsonObject object = array.get(array.size() - 1).getAsJsonObject();

            reader.close();
            input.close();

            final UUIDPair pair = new UUIDPair(uuid, object.get("name").getAsString());

            CACHE.put(pair.getUniqueId(), pair);

            return pair;
        }catch (final IOException exception){
            return UUIDPair.empty();
        }
    }

    @Nullable
    public UUIDPair getByName(@NotNull final String name){
        return CACHE.values()
                .stream()
                .filter(pair -> pair.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public UUIDPair getByUUID(@NotNull final UUID uuid){
        return CACHE.get(uuid);
    }

    @Getter
    @RequiredArgsConstructor
    public static class UUIDPair {

        public static UUIDPair empty(){
            return new UUIDPair(UUID.randomUUID(), "");
        }

        private final UUID uniqueId;
        private final String name;
    }
}
