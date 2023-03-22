package io.github.shiryu.spider.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Storage<I, T> {

    @NotNull
    public Optional<T> load(@NotNull final I id);
}
