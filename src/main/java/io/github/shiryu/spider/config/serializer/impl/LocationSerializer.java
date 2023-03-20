package io.github.shiryu.spider.config.serializer.impl;

import io.github.shiryu.spider.config.BasicConfiguration;
import io.github.shiryu.spider.config.serializer.ConfigSerializer;
import io.github.shiryu.spider.util.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class LocationSerializer implements ConfigSerializer<Location> {

    @Override
    public @NotNull Location read(@NotNull BasicConfiguration configuration, @NotNull final String path) {
        return SpiderLocation.from(
                configuration.getOrSet(path, "")
        ).getBukkitLocation();
    }

    @Override
    public void write(@NotNull BasicConfiguration configuration, @NotNull final Location location, @NotNull String path) {
        configuration.set(
                path,
                SpiderLocation.from(location)
                        .locationToString()
        );
    }
}
