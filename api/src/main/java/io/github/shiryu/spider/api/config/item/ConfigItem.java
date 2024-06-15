package io.github.shiryu.spider.api.config.item;

import io.github.shiryu.spider.api.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents a config item
 *
 * @param <T> the type of the config item
 */
public interface ConfigItem<T> {

    /**
     * Get the value of the config item
     *
     * @return the value of the config item
     */
    @NotNull
    T getValue();

    /**
     * Get the safe value of the config item
     *
     * @return the safe value of the config item
     */
    @NotNull default Optional<T> safe(){
        return Optional.ofNullable(this.getValue());
    }

    /**
     * Save the value of the config item
     *
     * @param config the config to save
     * @param path the path to save
     */
    void save(@NotNull final Config config, @NotNull final String path);

    /**
     * Get the value of the config item
     *
     * @param config the config to get
     * @param path the path to get
     */
    void get(@NotNull final Config config, @NotNull final String path);

}
