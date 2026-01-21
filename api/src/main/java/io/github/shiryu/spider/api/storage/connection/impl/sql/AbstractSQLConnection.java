package io.github.shiryu.spider.api.storage.connection.impl.sql;

import io.github.shiryu.spider.api.storage.connection.StorageConnection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;

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

    public void upsert(@NotNull final String table, @NotNull final Map<String, Object> columns) {
        try {
            final StringJoiner keys = new StringJoiner(", ");
            final StringJoiner placeholders = new StringJoiner(", ");
            final StringJoiner updates = new StringJoiner(", ");

            for (String key : columns.keySet()) {
                keys.add(key);
                placeholders.add("?");

                // key = VALUES(key)
                updates.add(key + " = VALUES(" + key + ")");
            }

            final String sql =
                    "INSERT INTO " + table + " (" + keys + ") VALUES (" + placeholders + ") "
                            + "ON DUPLICATE KEY UPDATE " + updates + ";";

            try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
                int index = 1;
                for (Object value : columns.values()) {
                    ps.setObject(index++, value);
                }
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute UPSERT for table " + table, e);
        }
    }

    public void replace(@NotNull final String table, @NotNull final Map<String, Object> columns) {
        final StringJoiner keys = new StringJoiner(", ");
        final StringJoiner placeholders = new StringJoiner(", ");

        for (String key : columns.keySet()) {
            keys.add(key);
            placeholders.add("?");
        }

        final String sql = "REPLACE INTO " + table + " (" + keys + ") VALUES (" + placeholders + ");";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : columns.values()) {
                ps.setObject(index++, value);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute REPLACE for table " + table, e);
        }
    }

    public void insert(@NotNull final String table, @NotNull final Map<String, Object> columns) {
        final StringJoiner keys = new StringJoiner(", ");
        final StringJoiner placeholders = new StringJoiner(", ");

        for (String key : columns.keySet()) {
            keys.add(key);
            placeholders.add("?");
        }

        final String sql = "INSERT INTO " + table + " (" + keys + ") VALUES (" + placeholders + ");";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : columns.values()) {
                ps.setObject(index++, value);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute INSERT for table " + table, e);
        }
    }

    public boolean contains(@NotNull final String table, @NotNull final String column, @NotNull final String value) {
        final String sql = "SELECT COUNT(*) AS count FROM " + table + " WHERE " + column + " = ?";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (Exception ignored) {}

        return false;
    }

    public boolean tableExists(String tableName) {
        try {
            DatabaseMetaData meta = this.connection.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, tableName, null)) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to check if table exists: " + tableName, e);
        }
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
