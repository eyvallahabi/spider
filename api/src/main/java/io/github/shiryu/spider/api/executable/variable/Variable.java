package io.github.shiryu.spider.api.executable.variable;

import org.jetbrains.annotations.NotNull;

public interface Variable<T> {

    @NotNull
    String getName();

    @NotNull
    T getValue();

    void setValue(@NotNull final T value);
}
