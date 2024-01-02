package io.github.shiryu.spider.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface LoadableConfig extends Config {

    void create();

    @NotNull
    String getName();

    @NotNull
    String getResourcePath();

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

    @Nullable File getFile();
}
