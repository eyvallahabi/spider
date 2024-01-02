package io.github.shiryu.spider.api.config.item.impl;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GeneralConfigItem<T> implements ConfigItem<T> {

    private T value;

    @Override
    public void save(@NotNull Config config, @NotNull String path) {
        config.set(
                path,
                this.value
        );
    }

    @Override
    public void get(@NotNull Config config, @NotNull String path) {
        this.value = config.get(path);
    }

}
