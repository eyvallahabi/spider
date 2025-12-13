package io.github.shiryu.spider.config;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class LocationSerializer implements ConfigSerializer<Location> {

    @Override
    public @NonNull Location get(@NotNull Config config, @NotNull String path) {
        return SpiderLocation.from(config.getOrSet(path, "")).convert();
    }

    @Override
    public void set(@NotNull Config config, @NotNull String path, @NonNull Location value) {
        config.set(path, SpiderLocation.from(value).locationToString());
    }

}
