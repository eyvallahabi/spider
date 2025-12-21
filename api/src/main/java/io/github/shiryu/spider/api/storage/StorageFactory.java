package io.github.shiryu.spider.api.storage;

import io.github.shiryu.spider.api.storage.connection.impl.FlatFileConnection;
import io.github.shiryu.spider.api.storage.connection.impl.sql.MySQLConnection;
import io.github.shiryu.spider.api.storage.connection.impl.sql.SQLiteConnection;
import io.github.shiryu.spider.api.storage.impl.FlatFileStorage;
import io.github.shiryu.spider.api.storage.impl.SQLStorage;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@UtilityClass
public class StorageFactory {

    @NotNull
    public <T> Storage<T> file(@NotNull final Plugin plugin, @NotNull final String name, @NotNull final Class<T> type){
        final FlatFileConnection connection = new FlatFileConnection(name);

        connection.connect(plugin);

        return new FlatFileStorage<>(
                connection,
                type
        );
    }

    @NotNull
    public <T> Storage<T> sqlLite(@NotNull final Plugin plugin, @NotNull final File file, @NotNull final Class<T> type){
        final SQLiteConnection connection = new SQLiteConnection(file);

        connection.connect(plugin);

        return new SQLStorage<>(
                connection,
                type
        );
    }

    @NotNull
    public <T> Storage<T> mySQL(@NotNull final Plugin plugin, @NotNull final String host,
                                final int port,
                                @NotNull final String database,
                                @NotNull final String username,
                                @NotNull final String password,
                                @NotNull final Class<T> type){
        final MySQLConnection connection = new MySQLConnection(
                host,
                port,
                database,
                username,
                password
        );

        connection.connect(plugin);

        return new SQLStorage<>(
                connection,
                type
        );
    }

}
