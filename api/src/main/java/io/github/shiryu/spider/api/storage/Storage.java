package io.github.shiryu.spider.api.storage;

import io.github.shiryu.spider.api.storage.connection.StorageConnection;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface Storage<T> {

    @NotNull
    StorageConnection getConnection();

    @NotNull
    Map<UUID, T> getAll();

    void save(@NotNull final T object);

    T load(@NotNull final UUID uuid);

    boolean contains(@NotNull final UUID uuid);

}
