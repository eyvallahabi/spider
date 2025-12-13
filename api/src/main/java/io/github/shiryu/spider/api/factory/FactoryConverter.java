package io.github.shiryu.spider.api.factory;

import org.jetbrains.annotations.NotNull;

public interface FactoryConverter<T> {

    @NotNull
    String deserialize(@NotNull final T input);

    T serialize(@NotNull final String input);

}
