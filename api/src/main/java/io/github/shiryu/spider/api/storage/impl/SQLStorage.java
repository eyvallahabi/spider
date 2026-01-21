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
import java.util.*;

@Getter
@RequiredArgsConstructor
public class SQLStorage<T> implements Storage<T> {

    private final AbstractSQLConnection connection;
    private final Class<T> type;

    @Override
    public Map<UUID, T> getAll(){
        final Map<UUID, T> result = new HashMap<>();

        final Storable storable = this.type.getAnnotation(Storable.class);
        if (storable == null)
            throw new RuntimeException("Missing @Storable annotation on " + type.getSimpleName());

        final String identifier = storable.identifier();

        final String sql = "SELECT * FROM " + this.getTableName() + ";";

        try(final PreparedStatement ps  = this.connection.getConnection().prepareStatement(sql)){
            final ResultSet rs = ps.executeQuery();

            while(rs.next()){
                // 1) Önce UUID'yi oku (identifier sütunu)
                final String uuidString = rs.getString(identifier);
                if (uuidString == null)
                    throw new RuntimeException("Identifier column " + identifier + " is NULL in table " + this.getTableName());

                final UUID id = UUID.fromString(uuidString);

                // 2) UUID constructor ile instance yarat
                final T instance = type.getDeclaredConstructor(UUID.class).newInstance(id);

                // 3) Diğer field'ları doldur
                for (Field field : type.getDeclaredFields()) {
                    if (field.getAnnotation(Skip.class) != null)
                        continue;

                    final String columnName = field.getName();
                    if (columnName.equals(identifier))
                        continue;  // UUID zaten constructor'da setlendi

                    field.setAccessible(true);
                    final Class<?> fieldType = field.getType();

                    Object value = null;

                    if (fieldType == String.class) {
                        value = rs.getString(columnName);

                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        value = rs.getInt(columnName);

                    } else if (fieldType == long.class || fieldType == Long.class) {
                        value = rs.getLong(columnName);

                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        value = rs.getBoolean(columnName);

                    } else if (fieldType == double.class || fieldType == Double.class) {
                        value = rs.getDouble(columnName);

                    } else if (fieldType == float.class || fieldType == Float.class) {
                        value = rs.getFloat(columnName);

                    } else if (fieldType == UUID.class) {
                        String raw = rs.getString(columnName);
                        value = raw != null ? UUID.fromString(raw) : null;

                    } else if (Factories.supports(fieldType)) {
                        String raw = rs.getString(columnName);
                        if (raw != null)
                            value = Factories.deserialize(raw, fieldType);
                    } else {
                        throw new RuntimeException("Unsupported field type: " + fieldType.getSimpleName());
                    }

                    field.set(instance, value);
                }

                // 4) Map'e koy
                result.put(id, instance);
            }
        }catch (final Exception exception){
            throw new RuntimeException("Failed to load all rows for " + this.getTableName(), exception);
        }

        return result;
    }

    @Override
    public void save(@NonNull T object) {
        final Storable storable = object.getClass().getAnnotation(Storable.class);

        if (storable == null)
            return;

        if (!this.connection.tableExists(this.getTableName()))
            this.createTable(this.type);

        try{
            final Map<String, Object> columns = Maps.newHashMap();

            for (final Field field : object.getClass().getDeclaredFields()){
                if (field.getAnnotation(Skip.class) != null)
                    continue;

                field.setAccessible(true);

                final String columnName = field.getName();
                Object value = field.get(object);

                if (value instanceof UUID)
                    value = value.toString();
                else if (value instanceof Enum<?> enumVal)
                    value = enumVal.name();
                else if (Factories.supports(value.getClass()))
                    value = Factories.getFactory(value.getClass()).serialize(value);

                columns.put(columnName, value);
            }

            this.connection.upsert(object.getClass().getSimpleName().toLowerCase(Locale.ENGLISH) + "s", columns);
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

        if (!this.connection.tableExists(this.getTableName()))
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

                return instance;
            }
        }catch (final Exception exception){
            throw new RuntimeException("Failed to load object " + this.type.getSimpleName(), exception);
        }
    }

    private void createTable(@NotNull final Class<?> type) {
        final Storable storable = type.getAnnotation(Storable.class);
        if (storable == null)
            return;

        final String identifier = storable.identifier();
        final List<String> columns = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(Skip.class) != null)
                continue;

            String columnName = field.getName();
            Class<?> fieldType = field.getType();
            String columnType;

            if (fieldType == UUID.class) {
                columnType = "VARCHAR(36)";
            } else if (Factories.supports(fieldType)) {
                columnType = "TEXT";
            } else if (fieldType == String.class) {
                columnType = "TEXT";
            } else if (fieldType == int.class || fieldType == Integer.class) {
                columnType = "INT";
            } else if (fieldType == long.class || fieldType == Long.class) {
                columnType = "BIGINT";
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                columnType = "BOOLEAN";
            } else if (fieldType == double.class || fieldType == Double.class) {
                columnType = "DOUBLE";
            } else if (fieldType == float.class || fieldType == Float.class) {
                columnType = "FLOAT";
            } else {
                throw new RuntimeException("Unsupported field type " + fieldType.getSimpleName());
            }

            // Identifier olan alan NOT NULL olacak
            if (columnName.equals(identifier)) {
                columnType += " NOT NULL";
            }

            columns.add(columnName + " " + columnType);
        }

        // PRIMARY KEY alanını en sona ekliyoruz
        columns.add("PRIMARY KEY (" + identifier + ")");

        this.connection.createTable(this.getTableName(), columns.toArray(new String[0]));
    }

    private String getTableName(){
        return this.type.getSimpleName().toLowerCase(Locale.ENGLISH) + "s";
    }
}
