package io.github.shiryu.spider.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface LoadableConfig extends Config {

    /**
     * Create the config
     */
    void create();

    /**
     * Get the name of the config
     * @return the name of the config
     */
    @NotNull
    String getName();

    /**
     * Get the path of the config
     * @return the path of the config
     */
    @NotNull
    String getResourcePath();

    /**
     * Save the config
     */
    void save();

    /**
     * Save the config asynchronously
     */
    default void saveAsync(){
        new Thread(() ->{
            try{
                this.save();
            }catch (final Exception exception){
                exception.printStackTrace();
            }
        }).start();
    }

    /**
     * Get the file of the config
     * @return the file of the config
     */
    @Nullable File getFile();
}
