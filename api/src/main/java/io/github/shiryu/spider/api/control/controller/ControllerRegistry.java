package io.github.shiryu.spider.api.control.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface ControllerRegistry<T> {

    void register(@NotNull final Class<?> clazz);

    default void register(@NotNull final Class<?>... classes){
        Arrays.stream(classes).forEach(this::register);
    }

    @Nullable
    ControllerHolder find(@NotNull final String id);

    @Nullable
    T create(@NotNull final String raw);

    @NotNull
    default List<T> create(@NotNull final List<String> raw){
        return raw.stream().map(this::create).filter(Objects::nonNull).toList();
    }
}
