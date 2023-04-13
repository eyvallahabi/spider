package io.github.shiryu.spider.storage.connection.sql;

import io.github.shiryu.spider.storage.connection.StorageConnection;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface SQLConnection extends StorageConnection {

    @NotNull
    DataSource getSource();

    @Override
    default void close(){
        this.transform().ifPresent(connection ->{
            try{
                connection.close();
            }catch (final Exception exception){
                return;
            }
        });
    }

    @NotNull
    default Optional<Connection> transform(){
        try{
            return Optional.of(getSource().getConnection());
        }catch (final SQLException e){
            return Optional.empty();
        }
    }

}
