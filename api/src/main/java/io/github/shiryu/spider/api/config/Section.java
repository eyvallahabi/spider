package io.github.shiryu.spider.api.config;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface Section extends Config {

    /**
     * Get all keys in the section
     *
     * @return the keys
     *
     */
    @NotNull
    Set<String> getKeys();

    /**
     * Loop all the keys in the section
     *
     * @param consumer the consumer
     *
     */
    default void forEach(final Consumer<String> consumer){
        this.getKeys().forEach(consumer::accept);
    }
}
