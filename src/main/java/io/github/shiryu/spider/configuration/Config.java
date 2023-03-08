package io.github.shiryu.spider.configuration;

import io.github.shiryu.spider.configuration.value.BasicValue;
import io.github.shiryu.spider.configuration.value.ItemValue;
import io.github.shiryu.spider.configuration.value.LocationValue;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public interface Config {

    @NotNull
    String getName();

    @NotNull
    String getPath();

    @NotNull
    File getFile();

    @NotNull
    <T> Optional<T> get(@NotNull final String path);

    <T> T getOrSet(@NotNull final String path, @NotNull final T fallback);

    @NotNull
    default <T> ConfigValue<T> getValue(@NotNull final String path){
        return new BasicValue<>(path, getOrSet(path, null));
    }

    @NotNull
    default <T> ConfigValue<T> getValue(@NotNull final String path, @NotNull final Class<T> clazz){
        if (clazz.equals(Location.class)){
            return (ConfigValue<T>) new LocationValue(path);
        }

        if (clazz.equals(ItemValue.class)){
            return (ConfigValue<T>) new ItemValue(path);
        }

        return new BasicValue<>(path, getOrSet(path, null));
    }

    @NotNull
    Optional<Config> findFile(@NotNull final String name);

    void forEachFile(@NotNull final String path, @NotNull final Consumer<Config> consumer);

    @NotNull
    Optional<ConfigSection> getSection(@NotNull final String path);

    <T> void set(@NotNull final String path, @NotNull final T object);

    void load();

    void save();
}
