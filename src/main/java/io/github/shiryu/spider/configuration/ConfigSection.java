package io.github.shiryu.spider.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ConfigSection extends Config {

    @Override
    default String getName(){
        return "";
    }

    void forEach(@NotNull final Consumer<String> consumer);
}
