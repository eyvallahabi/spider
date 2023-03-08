package io.github.shiryu.spider.configuration;

import org.jetbrains.annotations.NotNull;

public interface ConfigValue<T> {

    @NotNull
    String getPath();

    @NotNull
    T getValue();

    ConfigValue<T> load(@NotNull final Config config);

    ConfigValue<T> save(@NotNull final Config config);
}
