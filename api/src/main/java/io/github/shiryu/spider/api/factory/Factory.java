package io.github.shiryu.spider.api.factory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Factory {

    String serialize(@NotNull final Object object);

    @NotNull
    <T> T deserialize(@NotNull final String string, @NotNull final Class<T> type);

    <T> void register(@NotNull final Class<T> clazz, @NotNull final FactoryConverter<T> converter);

    @Nullable
    <T> FactoryConverter<T> getConverter(@NotNull final Class<T> type);

    boolean supports(@NotNull final Class<?> clazz);

}
