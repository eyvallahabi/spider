package io.github.shiryu.spider.bukkit.config;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.LoadableConfig;
import io.github.shiryu.spider.api.config.handler.ConfigHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public class BukkitConfigHandler implements ConfigHandler {

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
    public @NotNull LoadableConfig resource(@NotNull String name, @NotNull String path) {
        return null;
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
