package io.github.shiryu.spider.bukkit.config.item;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import io.github.shiryu.spider.bukkit.util.SpiderLocation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class LocationConfigItem implements ConfigItem<Location> {

    private Location value;

    @Override
    public void save(@NotNull Config config, @NotNull String path) {
        config.set(
                path,
                SpiderLocation.from(this.value)
                        .locationToString()
        );
    }

    @Override
    public void get(@NotNull Config config, @NotNull String path) {
        this.value = SpiderLocation.from(config.getOrSet(path, "")).getBukkitLocation();
    }
}
