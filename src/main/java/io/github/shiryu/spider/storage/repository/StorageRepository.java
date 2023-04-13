package io.github.shiryu.spider.storage.repository;

import io.github.shiryu.spider.storage.connection.StorageConnection;
import io.github.shiryu.spider.storage.entity.EntityParameter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface StorageRepository<T> {

    @NotNull
    StorageConnection getConnection();

    void push(@NotNull final UUID uuid, @NotNull final EntityParameter parameter);

    @NotNull
    Optional<T> load(@NotNull final UUID uuid);
}
