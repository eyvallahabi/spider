package io.github.shiryu.spider.configuration;

import org.jetbrains.annotations.NotNull;

public interface ConfigValue<T> {

    @NotNull
    String getPath();

    @NotNull
    T getValue();

    void load(@NotNull final Config config);

    void save(@NotNull final Config config);
}
