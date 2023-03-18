package io.github.shiryu.spider.config.serializer;

import io.github.shiryu.spider.config.BasicConfiguration;
import org.jetbrains.annotations.NotNull;

public interface ConfigSerializer<T> {

    T read(@NotNull final BasicConfiguration configuration, @NotNull final String path);

    void write(@NotNull final BasicConfiguration configuration, @NotNull final T object, @NotNull final String path);
}
