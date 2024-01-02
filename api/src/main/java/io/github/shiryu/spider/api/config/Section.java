package io.github.shiryu.spider.api.config;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface Section extends Config {

    @NotNull
    Set<String> getKeys();

    default void forEach(final Consumer<String> consumer){
        this.getKeys().forEach(consumer::accept);
    }
}
