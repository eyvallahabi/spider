package io.github.shiryu.spider.api.config.item;

import io.github.shiryu.spider.api.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ConfigItem<T> {

    @NotNull
    T getValue();

    @NotNull default Optional<T> safe(){
        return Optional.ofNullable(this.getValue());
    }

    void save(@NotNull final Config config, @NotNull final String path);

    void get(@NotNull final Config config, @NotNull final String path);

}
