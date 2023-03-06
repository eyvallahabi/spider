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
    public void load(@NotNull final Config config) {
        config.set(
                this.path,
                SpiderLocation.from(this.value)
                        .locationToString()
        );
    }

    @Override
    public void save(@NotNull final Config config) {
        this.value = SpiderLocation.from(config.getOrSet(this.path, ""))
                .getBukkitLocation();
    }
}
