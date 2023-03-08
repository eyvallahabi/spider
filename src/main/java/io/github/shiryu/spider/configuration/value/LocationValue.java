package io.github.shiryu.spider.configuration.value;

import io.github.shiryu.spider.configuration.Config;
import io.github.shiryu.spider.configuration.ConfigValue;
import io.github.shiryu.spider.util.location.SpiderLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LocationValue implements ConfigValue<Location> {

    private final String path;

    private Location value;

    public LocationValue(@NotNull final String path){
        this(path, null);
    }

    @Override
    public ConfigValue<Location> load(@NotNull final Config config) {
        config.set(
                this.path,
                SpiderLocation.from(this.value)
                        .locationToString()
        );

        return this;
    }

    @Override
    public ConfigValue<Location> save(@NotNull final Config config) {
        this.value = SpiderLocation.from(config.getOrSet(this.path, ""))
                .getBukkitLocation();

        return this;
    }
}
