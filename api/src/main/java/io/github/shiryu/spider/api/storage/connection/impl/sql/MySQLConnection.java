package io.github.shiryu.spider.api.storage.connection.impl.sql;

import io.github.shiryu.spider.api.storage.connection.ConnectionType;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class MySQLConnection extends AbstractSQLConnection {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    @Override
    public @NotNull ConnectionType getType() {
        return ConnectionType.MYSQL;
    }

    @Override
    public void connect(@NotNull Plugin plugin) {
        try{
            this.connection = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                    this.username,
                    this.password
            );
        }catch (final Exception exception){
            throw new RuntimeException("Could not connect to MySQL database!", exception);
        }
    }
}
