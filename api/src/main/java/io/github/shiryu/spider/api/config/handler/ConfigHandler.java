package io.github.shiryu.spider.api.config.handler;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.LoadableConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface ConfigHandler {

    @NotNull
    Config load(@NotNull final String name, @NotNull final String path);

    @NotNull
    default Config load(@NotNull final String name){
        return this.load(name, "");
    }

    @NotNull
    LoadableConfig resource(@NotNull final String name, @NotNull final String path);

    @NotNull
    default LoadableConfig resource(@NotNull final String name){
        return this.resource(name, "");
    }

    void delete(@NotNull final Config config);

    default void deleteAll(@NotNull final Set<Config> configs){
        configs.forEach(this::delete);
    }

    void save(@NotNull final Config config);

    default void saveAll(@NotNull final Set<Config> configs){
        configs.forEach(this::save);
    }

}
