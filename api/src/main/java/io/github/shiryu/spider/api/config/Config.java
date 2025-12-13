package io.github.shiryu.spider.api.config;

import io.github.shiryu.spider.api.config.impl.Section;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface Config {

    @NotNull
    String getName();

    @Nullable
    String getPath();

    boolean create();

    void load();

    void save();

    default void saveAsync(){
        new Thread(() ->{
            try{
                this.save();
            }catch (final Exception exception){
                exception.printStackTrace();
            }
        }).start();
    }

    boolean contains(@NotNull final String path);

    void set(@NotNull final String path, final Object value);

    <T> T get(@NotNull final String path);

    <T> T get(@NotNull final String path, @NotNull final Class<T> clazz);

    @Nullable
    Section getSection(@NotNull final String path);

    default <T> T getOrSet(@NotNull final String path, @NotNull final T def){
        final T get = this.get(path);

        if (get == null){
            this.set(path, def);

            return def;
        }

        return get;
    }

    @Nullable
    File getFile();
}
