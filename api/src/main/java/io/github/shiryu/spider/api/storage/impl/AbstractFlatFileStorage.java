package io.github.shiryu.spider.api.storage.impl;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.factory.Factories;
import io.github.shiryu.spider.api.storage.Storable;
import io.github.shiryu.spider.api.storage.Storage;
import io.github.shiryu.spider.api.storage.connection.impl.FlatFileConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class AbstractFlatFileStorage<T> implements Storage<T> {

    private final FlatFileConnection connection;
    private final Class<T> type;

    private final Map<UUID, T> all = Maps.newHashMap();

    @Override
    public void save(@NonNull T object) {
        final Storable storable = object.getClass().getAnnotation(Storable.class);

        if (storable == null)
            return;

        try{
            final Field idField = object.getClass().getField(storable.identifier());

            idField.setAccessible(true);

            final UUID uuid = (UUID) idField.get(object);

            final File file = this.connection.getFiles().get(uuid);

            if (file == null)
                throw new RuntimeException("No file found for object " + object.getClass().getSimpleName() + " with id " + uuid);

            final FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            for (final Field field : object.getClass().getFields()){
                field.setAccessible(true);

                final Object value = field.get(object);

                if (value == null)
                    continue;

                if (Factories.supports(value.getClass())){
                    final String serialized = Factories.serialize(value);

                    config.set(field.getName(), serialized);
                }else{
                    config.set(field.getName(), value);
                }
            }

            config.save(file);
        }catch (final Exception exception){
            throw new RuntimeException("Failed to save object " + object.getClass().getSimpleName());
        }
    }

    @Override
    public T load(@NotNull String identifier) {
        final UUID uuid;

        try{
            uuid = UUID.fromString(identifier);
        }catch (final Exception exception){
            throw new IllegalArgumentException("Identifier " + identifier + " is not a valid UUID");
        }

        if (!this.all.containsKey(uuid)){
            final File file = this.connection.getFiles().get(uuid);

            if (file == null)
                throw new RuntimeException("No file found for id " + uuid);

            final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            try{
                final T instance = this.type.getDeclaredConstructor(UUID.class).newInstance(uuid);

                for (final Field field : this.type.getFields()){
                    field.setAccessible(true);

                    final Object value = configuration.get(field.getName());

                    if (value == null)
                        continue;

                    if (Factories.supports(field.getType())){
                        final Object deserialized = Factories.deserialize((String) value, field.getType());

                        field.set(instance, deserialized);
                    }else{
                        field.set(instance, value);
                    }
                }

                this.all.put(uuid, instance);
            }catch (final Exception exception){
                throw new RuntimeException("Failed to create instance of " + this.type.getSimpleName());
            }

        }

        return this.all.get(uuid);
    }
}
