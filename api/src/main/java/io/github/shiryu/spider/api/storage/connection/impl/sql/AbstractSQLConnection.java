package io.github.shiryu.spider.api.storage.connection.impl.sql;

import io.github.shiryu.spider.api.storage.connection.StorageConnection;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

@Getter
@Setter
public abstract class AbstractSQLConnection implements StorageConnection {

    protected Connection connection;


}
