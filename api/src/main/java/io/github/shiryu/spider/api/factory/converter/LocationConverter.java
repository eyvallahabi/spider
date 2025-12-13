package io.github.shiryu.spider.api.factory.converter;

import io.github.shiryu.spider.api.factory.FactoryConverter;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class LocationConverter implements FactoryConverter<Location> {

    @Override
    public @NotNull String deserialize(@NonNull Location input) {
        return SpiderLocation.from(input)
                .locationToString();
    }

    @Override
    public Location serialize(@NotNull String input) {
        return SpiderLocation.from(input)
                .convert();
    }
}
