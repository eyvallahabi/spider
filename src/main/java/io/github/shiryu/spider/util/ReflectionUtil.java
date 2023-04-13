package io.github.shiryu.spider.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

@UtilityClass
public class ReflectionUtil {

    @Nullable
    public Constructor<?> getConstructor(@NotNull final Class<?> parent, @NotNull final Class... classes){
        try{
            return parent.getConstructor(classes);
        }catch (final Exception exception){
            return null;
        }
    }

    @Nullable
    public Constructor<?> getConstructor(@NotNull final Class<?> parent){
        return getConstructor(parent, null);
    }

    @Nullable
    public <T> T create(@NotNull final Constructor<T> constructor, @NotNull final Object... parameters){
        try{
            return constructor.newInstance(parameters);
        }catch (final Exception exception){
            return null;
        }
    }

}
