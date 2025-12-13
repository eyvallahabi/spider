package io.github.shiryu.spider.api.config.serializer;

import io.github.shiryu.spider.api.config.Config;
import org.jetbrains.annotations.NotNull;

public interface ConfigSerializer<T>{

    @NotNull
    T get(@NotNull final Config config, @NotNull final String path);

    void set(@NotNull final Config config, @NotNull final String path, @NotNull final T value);

}
