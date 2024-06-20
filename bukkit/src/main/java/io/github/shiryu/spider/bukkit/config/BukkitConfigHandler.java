package io.github.shiryu.spider.bukkit.config;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.LoadableConfig;
import io.github.shiryu.spider.api.config.handler.ConfigHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BukkitConfigHandler implements ConfigHandler<Plugin> {

    @Override
    public @NotNull Config load(@NotNull String name, @NotNull String path) {
        return new BukkitConfig(
                new File(
                        path,
                        name + ".yml"
                )
        );
    }

    @Override
    public @NotNull LoadableConfig resource(@NotNull final Plugin plugin, @NotNull String name, @NotNull String path) {
        return new BukkitLoadableConfig(
                plugin,
                name,
                path
        );
    }

    @Override
    public void delete(@NotNull Config config) {
        try{
            config.getFile().delete();
        }catch (final Exception exception){

        }
    }

    @Override
    public void save(@NotNull Config config) {
        try{
            YamlConfiguration.loadConfiguration(config.getFile())
                            .save(config.getFile());
        }catch (final Exception exception){

        }
    }
}
