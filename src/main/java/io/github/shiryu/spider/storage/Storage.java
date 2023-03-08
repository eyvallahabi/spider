package io.github.shiryu.spider.storage;

import org.jetbrains.annotations.NotNull;

public interface Storage<E extends StorageExecutor> {

    @NotNull
    StorageConnection getConnection();

    @NotNull
    E getExecutor();

    default void connect(){
        this.getConnection().connect();
    }
}
