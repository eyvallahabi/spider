package io.github.shiryu.spider.storage.impl;

import io.github.shiryu.spider.storage.Storage;
import io.github.shiryu.spider.storage.connection.mongo.MongoConnection;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class AbstractMongoDatabase implements Storage {

    private final MongoConnection connection;

    public AbstractMongoDatabase(@NotNull final MongoConnection connection){
        this.connection = connection;
    }
}
