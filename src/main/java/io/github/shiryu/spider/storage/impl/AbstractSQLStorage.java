package io.github.shiryu.spider.storage.impl;

import io.github.shiryu.spider.storage.Storage;
import io.github.shiryu.spider.storage.connection.sql.SQLConnection;
import io.github.shiryu.spider.storage.repository.StorageRepository;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class AbstractSQLStorage implements Storage {

    private final SQLConnection connection;

    private final Map<Class<?>, StorageRepository<?>> repositories = new HashMap<>();

    public AbstractSQLStorage(@NotNull final SQLConnection connection){
        this.connection = connection;
    }


}
