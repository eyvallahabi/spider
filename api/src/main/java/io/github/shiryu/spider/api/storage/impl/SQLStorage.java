package io.github.shiryu.spider.api.storage.impl;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.factory.Factories;
import io.github.shiryu.spider.api.storage.Storable;
import io.github.shiryu.spider.api.storage.Storage;
import io.github.shiryu.spider.api.storage.annotations.Skip;
import io.github.shiryu.spider.api.storage.connection.impl.sql.AbstractSQLConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SQLStorage<T> implements Storage<T> {

    private final AbstractSQLConnection connection;
    private final Class<T> type;

    private final Map<UUID, T> all = Maps.newHashMap();

    @Override
    public void save(@NonNull T object) {
        final Storable storable = object.getClass().getAnnotation(Storable.class);

        if (storable == null)
            return;

        if (!this.connection.contains(this.getTableName()))
            this.createTable(this.type);

        try{
            final Map<String, String> columns = Maps.newHashMap();

            for (final Field field : object.getClass().getDeclaredFields()){
                if (field.getAnnotation(Skip.class) != null)
                    continue;

                field.setAccessible(true);

                final String columnName = field.getName();
                final Object value = field.get(object);

                String valueString;

                if (value instanceof String){
                    valueString = "'" + value + "'";
                }else{
                    valueString = String.valueOf(value);
                }

                columns.put(columnName, valueString);
            }

            this.connection.replace(object.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + "s", columns);
        }catch (final Exception exception){
            throw new RuntimeException("Failed to save object " + object.getClass().getSimpleName(), exception);
        }
    }

    @Override
    public boolean contains(@NotNull UUID uuid) {
        return this.connection.contains(
                this.getTableName(),
                "uuid",
                uuid.toString()
        );
    }

    @Override
    public T load(@NotNull UUID identifier) {
        final String query = "SELECT * FROM " + this.getTableName() + " WHERE uuid = ?;";

        if (!this.connection.contains(this.getTableName()))
            this.createTable(this.type);

        try{
            final T instance = this.type.getConstructor(UUID.class).newInstance(identifier);

            try(PreparedStatement ps = this.connection.getConnection().prepareStatement(query)){
                ps.setString(1, identifier.toString());

                final ResultSet set = ps.executeQuery();

                if (set.next()){
                    for (final Field field : this.type.getDeclaredFields()){
                        field.setAccessible(true);

                        final String columnName = field.getName();
                        final Class<?> fieldType = field.getType();

                        if (Factories.supports(fieldType)){
                            final String string = set.getString(columnName);

                            if (string == null)
                                continue;

                            field.set(instance, Factories.getFactory(fieldType).deserialize(string, fieldType));
                            continue;
                        }

                        if (fieldType == String.class){
                            field.set(instance, set.getString(columnName));
                        }else if (fieldType == int.class || fieldType == Integer.class){
                            field.set(instance, set.getInt(columnName));
                        }else if (fieldType == long.class || fieldType == Long.class){
                            field.set(instance, set.getLong(columnName));
                        }else if (fieldType == boolean.class || fieldType == Boolean.class){
                            field.set(instance, set.getBoolean(columnName));
                        }else if (fieldType == double.class || fieldType == Double.class){
                            field.set(instance, set.getDouble(columnName));
                        }else if (fieldType == float.class || fieldType == Float.class){
                            field.set(instance, set.getFloat(columnName));
                        }else if (fieldType == UUID.class){
                            field.set(instance, UUID.fromString(set.getString(columnName)));
                        }
                    }
                }

                this.all.put(identifier, instance);

                return instance;
            }
        }catch (final Exception exception){
            throw new RuntimeException("Failed to load object " + this.type.getSimpleName(), exception);
        }
    }

    private void createTable(@NotNull final Class<?> type){
        this.connection.createTable(
                this.getTableName(),
                Arrays.stream(type.getDeclaredFields())
                        .map(field -> {
                            String columnType;

                            final Class<?> fieldType = field.getType();

                            if (Factories.supports(fieldType)){
                                columnType = "TEXT";
                            }else if (fieldType == String.class){
                                columnType = "TEXT";
                            }else if (fieldType == int.class || fieldType == Integer.class){
                                columnType = "INT";
                            }else if (fieldType == long.class || fieldType == Long.class){
                                columnType = "BIGINT";
                            }else if (fieldType == boolean.class || fieldType == Boolean.class){
                                columnType = "BOOLEAN";
                            }else if (fieldType == double.class || fieldType == Double.class){
                                columnType = "DOUBLE";
                            }else if (fieldType == float.class || fieldType == Float.class){
                                columnType = "FLOAT";
                            }else if (fieldType == UUID.class){
                                columnType = "VARCHAR(36)";
                            }else{
                                throw new RuntimeException("Unsupported field type " + fieldType.getSimpleName() + " for SQL storage");
                            }

                            return field.getName() + " " + columnType;
                        })
                        .toArray(String[]::new)
        );
    }

    private String getTableName(){
        return this.type.getSimpleName().toLowerCase(Locale.ENGLISH) + "s";
    }
}
