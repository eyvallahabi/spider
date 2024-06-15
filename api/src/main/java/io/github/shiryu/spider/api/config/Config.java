package io.github.shiryu.spider.api.config;

import io.github.shiryu.spider.api.config.item.ConfigItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface Config {

    /**
     * Check if the config has a path
     * @param path the path to check
     * @return true if the path exists
     */
    boolean has(@NotNull final String path);

    /**
     * Get a section from the config
     * @param path the path to the section
     * @return the section or null if it doesn't exist
     */
    @Nullable
    Section getSection(@NotNull final String path);

    /**
     * Set a path in the config
     * @param path the path to set
     * @param object the object to set
     */
    void set(@NotNull final String path, @NotNull final Object object);

    /**
     * Get a path from the config
     * @param path the path to get
     * @return the object or null if it doesn't exist
     */
    <T> T get(@NotNull final String path);

    /**
     * Get a path from the config or set it if it doesn't exist
     * @param path the path to get
     * @param fallback the fallback object to set if the path doesn't exist
     * @param <T> the type of the object
     * @return the object
     */
    default <T> T getOrSet(@NotNull final String path, @NotNull final T fallback){
        if (fallback == null) return null;

        final T get = (T) this.get(path);

        if (get == null){
            this.set(path, fallback);

            return fallback;
        }

        return get;
    }

    /**
     * Get a custom object from the config
     * @param path the path to get
     * @param clazz the class of the object
     * @param <T> the type of the object
     * @return the object
     */
    @NotNull
    <T> ConfigItem<T> getItem(@NotNull final String path, @NotNull final Class<T> clazz);

    /**
     * Get file of the config
     * @return the file
     */
    @Nullable
    File getFile();
}
