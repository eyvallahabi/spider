package io.github.shiryu.spider.storage.entity;

import io.github.shiryu.spider.storage.annotations.Entity;
import io.github.shiryu.spider.storage.annotations.Id;
import io.github.shiryu.spider.storage.entity.parameter.EntityParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StorageEntity {

    private final Object parent;

    private EntityParameter id;

    private final List<EntityParameter> parameters = new ArrayList<>();

    public StorageEntity(@NotNull final Object parent){
        this.parent = parent;

        this.init();
    }

    private void init(){
        final Class<?> clazz = this.parent.getClass();

        if (!clazz.isAnnotationPresent(Entity.class))
            return;

        for (final Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);

            final EntityParameter parameter = new EntityParameter(field.getName(), field.getType());

            Object value;

            try{
                value = field.get(this.parent);
            }catch (final Exception exception){
                value = null;
            }

            parameter.setValue(value);

            if (field.isAnnotationPresent(Id.class)) {
                this.id = parameter;
            }else{
                this.parameters.add(parameter);
            }
        }
    }

    @Nullable
    public <T> EntityParameter<T> getParameter(@NotNull final String id, @NotNull final Class<T> clazz){
        return this.parameters.stream()
                .filter(parameter -> parameter.getId().equals(id) && parameter.getType().equals(clazz))
                .findFirst()
                .orElse(null);
    }
}
