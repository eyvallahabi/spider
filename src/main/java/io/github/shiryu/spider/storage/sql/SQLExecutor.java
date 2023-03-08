package io.github.shiryu.spider.storage.sql;

import io.github.shiryu.spider.storage.StorageExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@RequiredArgsConstructor
public class SQLExecutor implements StorageExecutor {

    private final SQLConnection connection;

    public void createTable(@NotNull final String table, @NotNull final String key,
                            @NotNull final Object object) {
        this.createTable(table, Collections.singletonMap(key, object));
    }

    @SafeVarargs
    public final void createTable(@NotNull final String table, @NotNull final Map.Entry<String, Object>... parameters) {
        this.createTable(table, Arrays.asList(parameters));
    }

    public void createTable(@NotNull final String table,
                            @NotNull final Collection<Map.Entry<String, Object>> parameters) {
        this.createTable(table, parameters.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public void createTable(@NotNull final String table, @NotNull final Map<String, Object> parameters) {
        update(
                "CREATE TABLE IF NOT EXISTS " + table + " (" + buildCompleteValues(parameters) + ");"
        );
    }

    @SneakyThrows
    public void set(@NotNull final String table, @NotNull final String selected, @NotNull final Object object,
                    @NotNull final String column, @NotNull final String logic, @NotNull final String data) {
        update("UPDATE " + table + " SET " + selected + " = '" + object + "' WHERE " + column + ' ' + logic + " '" + data + "';");
    }

    @SneakyThrows
    public void deleteData(@NotNull final String table, @NotNull final String column, @NotNull final String logic,
                           @NotNull final String data) {
        update("DELETE FROM " + table + " WHERE " + column + ' ' + logic + " '" + data + "';");
    }

    public void insertData(@NotNull final String table, @NotNull final String key, @NotNull final Object object) {
        this.insertData(table, Collections.singletonMap(key, object));
    }

    @SafeVarargs
    public final void insertData(@NotNull final String table, @NotNull final Map.Entry<String, Object>... parameters) {
        this.insertData(table, Arrays.asList(parameters));
    }

    public void insertData(@NotNull final String table, @NotNull final List<Map.Entry<String, Object>> parameters) {
        this.insertData(table, parameters.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @SneakyThrows
    public void insertData(@NotNull final String table, @NotNull final Map<String, Object> parameters){
        update("INSERT INTO " + table + " (" + buildKeys(parameters) + ") VALUES(" + buildValues(parameters) + ");");
    }

    @SneakyThrows
    public void deleteTable(@NotNull final String table){
        update(
                "DELETE FROM " + table
        );
    }

    @SneakyThrows
    public boolean tableExists(@NotNull final String table) {
        final AtomicBoolean result = new AtomicBoolean(false);

        this.getConnection().transform()
                .ifPresent(connection ->{
                    try{
                        result.set(connection.getMetaData()
                                .getTables(null, null, table, null)
                                .next());
                    }catch (final Exception exception){

                    }
                });

        return result.get();
    }

    @SneakyThrows
    public boolean columnExists(@NotNull final String table, @NotNull final String column) {
        final AtomicBoolean result = new AtomicBoolean(false);

        this.getConnection().transform()
                .ifPresent(connection ->{
                    try{
                        result.set(
                                connection.getMetaData()
                                        .getColumns(null, null, table, column)
                                        .next()
                        );
                    }catch (final Exception exception){

                    }
                });

        return result.get();
    }

    @SneakyThrows
    public boolean exists(@NotNull final String table, @NotNull final String column, @NotNull final Object data) {
        return query("SELECT * FROM " + table + " WHERE " + column + " = '" + data + "';").next();
    }

    @SneakyThrows
    @Nullable
    public Object get(@NotNull final String table, @NotNull final String selected,
                      @NotNull final String column, @NotNull final String logic,
                      @NotNull final Object object) {
        final ResultSet rs = query("SELECT * FROM " + table + " WHERE " + column + ' ' + logic + " '" + object + "';");

        if (rs.next())
            return rs.getObject(selected);

        return null;
    }

    @Nullable
    public ResultSet query(@Language("MySQL") final String query){
        final AtomicReference<ResultSet> reference = new AtomicReference<>();

        getConnection().transform()
                .ifPresent(connection ->{
                    try{
                        reference.set(
                                connection.createStatement()
                                        .executeQuery(query)
                        );
                    }catch (final SQLException e){
                        throw new RuntimeException(e);
                    }
                });

        return reference.get();
    }

    public void update(@Language("MySQL") final String query){
        getConnection().transform()
                .ifPresent(connection ->
                        {
                            try {
                                connection.createStatement()
                                        .executeUpdate(query);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }

    @NotNull
    private String buildKeys(@NotNull final Map<String, Object> convert) {
        final List<String> keys = new ArrayList<>(convert.keySet());
        final StringBuilder builder = new StringBuilder();

        IntStream.range(0, keys.size()).forEach(index -> {
            builder.append(keys.get(index));
            if (index < keys.size() - 1) {
                builder.append(", ");
            }
        });

        return builder.toString();
    }

    @NotNull
    private String buildCompleteValues(@NotNull final Map<String, Object> parameters) {
        final List<Map.Entry<String, Object>> entries = new ArrayList<>(parameters.entrySet());

        final StringBuilder sb = new StringBuilder();

        for (int index = 0; index < entries.size(); index++) {
            final String key = entries.get(index).getKey();
            final String value = String.valueOf(entries.get(index).getValue());

            if (!key.isEmpty())
                sb.append(key);

            if (!value.isEmpty())
                sb.append(' ').append(value);

            if (index < parameters.size() - 1)
                sb.append(',').append(' ');

        }

        return sb.toString();
    }

    @NotNull
    private String buildValues(@NotNull final Map<String, Object> parameters) {
        final List<Map.Entry<String, Object>> entries = new ArrayList<>(parameters.entrySet());
        final StringBuilder sb = new StringBuilder();

        for (int index = 0; index < entries.size(); index++) {
            final String value = String.valueOf(entries.get(index).getValue());

            if (!value.isEmpty())
                sb.append(' ').append("\'").append(value).append("\'");

            if (index < parameters.size() - 1)
                sb.append(',');
        }

        return sb.toString();
    }
}
