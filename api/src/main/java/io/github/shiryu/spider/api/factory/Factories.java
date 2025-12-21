package io.github.shiryu.spider.api.factory;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.factory.impl.AbstractFactory;
import io.github.shiryu.spider.api.factory.impl.ItemStackFactory;
import io.github.shiryu.spider.api.factory.impl.LocationFactory;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UtilityClass
public class Factories {

    @Getter
    private final List<Factory> factories = Lists.newArrayList(
            new ItemStackFactory(),
            new LocationFactory()
    );

    public void registerAll(){
        factories.stream()
                .map(factory -> (AbstractFactory) factory)
                .forEach(AbstractFactory::register);
    }

    @NotNull
    public <T> T deserialize(@NotNull final String string, @NotNull final Class<T> type){
        final Factory factory = getFactory(type);

        if (factory == null)
            throw new IllegalArgumentException("No factory found for type: " + type.getName());

        return factory.deserialize(string, type);
    }

    @NotNull
    public String serialize(@NotNull final Object object){
        final Factory factory = getFactory(object.getClass());

        if (factory == null)
            throw new IllegalArgumentException("No factory found for type: " + object.getClass().getName());

        return factory.serialize(object);
    }

    public boolean supports(@NotNull final Class<?> type){
        return getFactory(type) != null;
    }

    public <T> Factory getFactory(@NotNull final Class<T> type){
        return factories.stream()
                .filter(factory -> factory.supports(type))
                .findFirst()
                .orElse(null);
    }
}
