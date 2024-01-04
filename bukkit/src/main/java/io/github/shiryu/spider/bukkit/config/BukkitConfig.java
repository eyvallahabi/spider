package io.github.shiryu.spider.bukkit.config;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.Section;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import io.github.shiryu.spider.api.config.item.impl.EmptyConfigItem;
import io.github.shiryu.spider.api.config.item.impl.GeneralConfigItem;
import io.github.shiryu.spider.bukkit.config.item.LocationConfigItem;
import io.github.shiryu.spider.bukkit.config.item.PotionEffectConfigItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Getter
public class BukkitConfig implements Config {

    private final File file;

    private YamlConfiguration configuration;

    public BukkitConfig(@NotNull final File file){
        this.file = file;

        try{
            this.configuration = YamlConfiguration.loadConfiguration(file);
        }catch (final Exception exception){
            this.configuration = null;
        }
    }

    @Override
    public boolean has(@NotNull final String path) {
        return this.configuration.contains(path);
    }

    @Override
    public @Nullable Section getSection(@NotNull String path) {
        return null;
    }

    @Override
    public void set(@NotNull String path, @NotNull Object object) {
        this.configuration.set(path, object);
    }

    @Override
    public <T> T get(@NotNull String path) {
        return (T) this.configuration.get(path);
    }

    @Override
    public @NotNull <T> ConfigItem<T> getItem(@NotNull String path, @NotNull final Class<T> clazz) {
        if (clazz.equals(Location.class))
            return (ConfigItem<T>) new LocationConfigItem();

        if (clazz.equals(PotionEffect.class))
            return (ConfigItem<T>) new PotionEffectConfigItem();

        
        return new GeneralConfigItem<>();
    }

}
