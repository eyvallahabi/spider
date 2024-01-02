package io.github.shiryu.spider.api.config.item.impl;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import org.jetbrains.annotations.NotNull;

public class EmptyConfigItem<T> implements ConfigItem<T> {

    @Override
    public @NotNull T getValue() {
        return null;
    }

    @Override
    public void save(@NotNull Config config, @NotNull String path) {

    }

    @Override
    public void get(@NotNull Config config, @NotNull String path) {}
}
