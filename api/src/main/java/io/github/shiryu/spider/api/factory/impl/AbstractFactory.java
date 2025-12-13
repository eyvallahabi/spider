package io.github.shiryu.spider.api.factory.impl;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.factory.Factory;
import io.github.shiryu.spider.api.factory.FactoryConverter;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public abstract class AbstractFactory implements Factory {

    private final Map<Class<?>, FactoryConverter<?>> converters = Maps.newHashMap();

    public abstract void register();

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return converters.containsKey(clazz);
    }

    @Override
    public <T> void register(@NotNull Class<T> clazz, @NotNull FactoryConverter<T> converter) {
        this.converters.put(clazz, converter);
    }

    @Override
    public String serialize(@NotNull Object object) {
        final FactoryConverter converter = this.getConverter(object.getClass());

        if (converter == null)
            throw new IllegalArgumentException("No converter found for type: " + object.getClass().getName());

        return converter.deserialize(object);
    }

    @Override
    public @NonNull <T> T deserialize(@NotNull String string, @NotNull Class<T> type) {
        final FactoryConverter<T> converter = this.getConverter(type);

        if (converter == null)
            throw new IllegalArgumentException("No converter found for type: " + type.getName());

        return converter.serialize(string);
    }

    @Override
    public @Nullable <T> FactoryConverter<T> getConverter(@NotNull Class<T> type) {
        //noinspection unchecked
        return (FactoryConverter<T>) this.converters.get(type);
    }
}
