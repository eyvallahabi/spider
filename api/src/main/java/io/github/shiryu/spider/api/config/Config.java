package io.github.shiryu.spider.api.config;

import io.github.shiryu.spider.api.config.item.ConfigItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface Config {

    boolean has(@NotNull final String path);

    @Nullable
    Section getSection(@NotNull final String path);

    void set(@NotNull final String path, @NotNull final Object object);

    <T> T get(@NotNull final String path);

    default <T> T getOrSet(@NotNull final String path, @NotNull final T fallback){
        if (fallback == null) return null;

        final T get = (T) this.get(path);

        if (get == null){
            this.set(path, fallback);

            return fallback;
        }

        return get;
    }

    @NotNull
    <T> ConfigItem<T> getItem(@NotNull final String path, @NotNull final Class<T> clazz);

    @Nullable
    File getFile();
}
