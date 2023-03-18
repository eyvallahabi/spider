package io.github.shiryu.spider.location.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.github.shiryu.spider.SpiderPlugin;
import io.github.shiryu.spider.location.SpiderLocation;

import java.io.IOException;

public class LocationTypeFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!SpiderLocation.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter jsonWriter, T location) throws IOException {
                if (location == null) {
                    jsonWriter.nullValue();
                } else {
                    SpiderPlugin.getPlugin()
                                    .getGson()
                                    .toJson(((SpiderLocation)location).locationToString(), String.class, jsonWriter);
                }
            }

            @Override
            public T read(JsonReader reader) throws IOException {
                if (reader.peek() == null) {
                    reader.nextNull();
                    return null;
                } else {
                    return (T) SpiderLocation.from(
                            (String)
                            SpiderPlugin.getPlugin()
                                    .getGson()
                                    .fromJson(reader, String.class)
                    );
                }
            }
        };
    }

}
