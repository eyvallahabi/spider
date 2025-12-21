package io.github.shiryu.spider.api.storage.connection.impl.sql;

import io.github.shiryu.spider.api.storage.connection.StorageConnection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Map;

@Getter
@Setter
public abstract class AbstractSQLConnection implements StorageConnection {

    protected Connection connection;


    @Override
    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }

            this.connection = null;
        } catch (Exception ignored) {

        }
    }

    @Override
    public boolean isConnected() {
        return this.connection != null;
    }

    public void execute(@NotNull final String query){
        try{
            this.connection.createStatement().executeUpdate(query);
        }catch (Exception ignored){

        }
    }

    public void replace(@NotNull final String table, @NotNull final Map<String, String> columns){
        final StringBuilder columnsPart = new StringBuilder();
        final StringBuilder valuesPart = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            columnsPart.append(entry.getKey());
            valuesPart.append(entry.getValue());

            if (i < columns.size() - 1) {
                columnsPart.append(", ");
                valuesPart.append(", ");
            }
            i++;
        }

        final String query = "REPLACE INTO " + table + " (" + columnsPart + ") VALUES (" + valuesPart + ");";
        execute(query);
    }

    public void insert(@NotNull final String table, @NotNull final Map<String, String> columns){
        final StringBuilder columnsPart = new StringBuilder();
        final StringBuilder valuesPart = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            columnsPart.append(entry.getKey());
            valuesPart.append(entry.getValue());

            if (i < columns.size() - 1) {
                columnsPart.append(", ");
                valuesPart.append(", ");
            }
            i++;
        }

        final String query = "INSERT INTO " + table + " (" + columnsPart + ") VALUES (" + valuesPart + ");";
        execute(query);
    }

    public boolean contains(@NotNull final String table, @NotNull final String column, @NotNull final String value){
        try{
            final String query = "SELECT COUNT(*) AS count FROM " + table + " WHERE " + column + " = '" + value + "';";
            final var statement = this.connection.createStatement();
            final var resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                return resultSet.getInt("count") > 0;
            }
        }catch (final Exception ignored){

        }
        return false;
    }

    public boolean contains(@NotNull final String table){
        try{
            final DatabaseMetaData meta = connection.getMetaData();
            final ResultSet result = meta.getTables(null, null, table, null);

            return result.next();
        }catch (final Exception exception){
            return false;
        }
    }

    public void createTable(@NotNull final String name, @NotNull final String... columns){
        final StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name + " (");

        for(int i = 0; i < columns.length; i++){
            builder.append(columns[i]);
            if(i != columns.length - 1)
                builder.append(", ");
        }

        builder.append(");");

        execute(builder.toString());
    }

}
