package io.github.shiryu.spider.api.integration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IntegrationRegistry {

    void register(@NotNull final Class<? extends Integration> clazz);

    void register(@NotNull final String name, @NotNull final Class<? extends Integration> clazz);

    void register(@NotNull final Integration integration);

    boolean isRegistered(@NotNull final String name);

    boolean isRegistered(@NotNull final Class<? extends Integration> clazz);

    @Nullable
    <T extends Integration> T get(@NotNull final Class<T> clazz);
}
