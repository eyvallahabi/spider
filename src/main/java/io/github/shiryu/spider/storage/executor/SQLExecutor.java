package io.github.shiryu.spider.storage.executor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public final class SQLExecutor {

    @NotNull
    @Getter
    private final DataSource source;

    @NotNull
    private static String buildKeys(@NotNull final Map<String, Object> convert) {
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
    private static String buildCompleteValues(@NotNull final Map<String, Object> parameters) {
        final List<Map.Entry<String, Object>> entries = new ArrayList<>(parameters.entrySet());
        final StringBuilder sb = new StringBuilder();
        for (int index = 0; index < entries.size(); index++) {
            final String key = entries.get(index).getKey();
            final String value = String.valueOf(entries.get(index).getValue());
            if (!key.isEmpty()) {
                sb.append(key);
            }
            if (!value.isEmpty()) {
                sb.append(' ').append('\'').append(value).append('\'');
            }
            if (index < parameters.size() - 1) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }

    @NotNull
    private static String buildValues(@NotNull final Map<String, Object> parameters) {
        final List<Map.Entry<String, Object>> entries = new ArrayList<>(parameters.entrySet());
        final StringBuilder sb = new StringBuilder();
        for (int index = 0; index < entries.size(); index++) {
            final String value = String.valueOf(entries.get(index).getValue());
            if (!value.isEmpty()) {
                sb.append(' ').append('\'').append(value).append('\'');
            }
            if (index < parameters.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

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

    @SneakyThrows
    public void createTable(@NotNull final String table, @NotNull final Map<String, Object> parameters) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (" + SQLExecutor.buildCompleteValues(parameters) + ");");
        }
    }

    @SneakyThrows
    public void set(@NotNull final String table, @NotNull final String selected, @NotNull final Object object,
                    @NotNull final String column, @NotNull final String logic, @NotNull final String data) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE " + table + " SET " + selected + " = '" + object + "' WHERE " + column + ' ' + logic + " '" + data + "';");
        }
    }

    @SneakyThrows
    public void close() {
        this.source.getConnection().close();
    }

    @SneakyThrows
    public void deleteData(@NotNull final String table, @NotNull final String column, @NotNull final String logic,
                           @NotNull final String data) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + table + " WHERE " + column + ' ' + logic + " '" + data + "';");
        }
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
    public void insertData(@NotNull final String table, @NotNull final Map<String, Object> parameters) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO " + table + " (" + SQLExecutor.buildKeys(parameters) + ") VALUES(" + SQLExecutor.buildValues(parameters) + ");");
        }
    }

    @SneakyThrows
    public void deleteTable(@NotNull final String table) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + table);
        }
    }

    @SneakyThrows
    public boolean tableExists(@NotNull final String table) {
        try (final Connection connection = this.source.getConnection()) {
            return connection.getMetaData()
                .getTables(null, null, table, null)
                .next();
        }
    }

    @SneakyThrows
    public boolean columnExists(@NotNull final String table, @NotNull final String column) {
        try (final Connection connection = this.source.getConnection()) {
            return connection.getMetaData()
                .getColumns(null, null, table, column)
                .next();
        }
    }

    @SneakyThrows
    public boolean exists(@NotNull final String table, @NotNull final String column, @NotNull final Object data) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            try (final ResultSet rs = statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + data + "';")) {
                return rs.next();
            }
        }
    }

    @SneakyThrows
    @NotNull
    public Optional<Object> get(@NotNull final String table, @NotNull final String selected,
                                @NotNull final String column, @NotNull final String logic,
                                @NotNull final Object object) {
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            try (final ResultSet rs = statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + ' ' + logic + " '" + object + "';")) {
                if (rs.next()) {
                    return Optional.ofNullable(rs.getObject(selected));
                }
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    @NotNull
    public <T> List<T> listGet(@NotNull final String table, @NotNull final String selected,
                               @NotNull final String column, @NotNull final String logic,
                               @NotNull final Object object, @NotNull final Class<T> type) {
        final List<T> list = new ArrayList<>();
        try (final Connection connection = this.source.getConnection();
             final Statement statement = connection.createStatement()) {
            try (final ResultSet rs = statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + ' ' + logic + " '" + object + "';")) {
                while (rs.next()) {
                    final Object rsobject = rs.getObject(selected);
                    if (type.isAssignableFrom(rsobject.getClass())) {
                        list.add((T) rsobject);
                    }
                }
            }
        }
        return list;
    }

}