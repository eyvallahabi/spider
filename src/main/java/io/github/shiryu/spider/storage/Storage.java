package io.github.shiryu.spider.storage;

import io.github.shiryu.spider.storage.connection.StorageConnection;
import io.github.shiryu.spider.storage.repository.StorageRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public interface Storage {

    @NotNull
    StorageConnection getConnection();

    @NotNull
    Map<Class<?>, StorageRepository<?>> getRepositories();

    @Nullable
    default <T> StorageRepository<T> getRepository(@NotNull final Class<T> clazz){
        return (StorageRepository<T>) getRepositories().get(clazz);
    }

    default <T> void register(@NotNull final Class<T> clazz, @NotNull final StorageRepository<T> repository){
        if (getRepository(clazz) != null)
            return;

        getRepositories().put(clazz, repository);
    }

}
