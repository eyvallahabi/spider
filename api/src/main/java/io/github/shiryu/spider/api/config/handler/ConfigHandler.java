package io.github.shiryu.spider.api.config.handler;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.LoadableConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * A handler for managing configurations
 */
public interface ConfigHandler {

    /**
     * Load a configuration with name and the path
     *
     * @param name the name of the configuration
     * @param path the path of the configuration
     * @return the loaded configuration
     */
    @NotNull
    Config load(@NotNull final String name, @NotNull final String path);

    /**
     * Load a configuration only with name
     *
     * @param name the name of the configuration
     * @return the loaded configuration
     */
    @NotNull
    default Config load(@NotNull final String name){
        return this.load(name, "");
    }

    /**
     * Load a configuration from the resource
     *
     * @param name the name of the configuration
     * @param path the path of the configuration
     * @return the loaded configuration
     */
    @NotNull
    LoadableConfig resource(@NotNull final String name, @NotNull final String path);

    /**
     * Load a configuration from the resource
     *
     * @param name the name of the configuration
     * @return the loaded configuration
     */
    @NotNull
    default LoadableConfig resource(@NotNull final String name){
        return this.resource(name, "");
    }

    /**
     * Delete a configuration
     *
     * @param config the configuration to delete
     */
    void delete(@NotNull final Config config);

    /**
     * Delete all configurations
     *
     * @param configs the configurations to delete
     */
    default void deleteAll(@NotNull final Set<Config> configs){
        configs.forEach(this::delete);
    }

    /**
     * Save a configuration
     *
     * @param config the configuration to save
     */
    void save(@NotNull final Config config);

    /**
     * Save all configurations
     *
     * @param configs the configurations to save
     */
    default void saveAll(@NotNull final Set<Config> configs){
        configs.forEach(this::save);
    }

}
