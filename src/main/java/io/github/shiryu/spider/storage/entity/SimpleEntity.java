package io.github.shiryu.spider.storage.entity;

import io.github.shiryu.spider.storage.annotations.Entity;
import io.github.shiryu.spider.storage.annotations.Id;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SimpleEntity {

    private final Object parent;

    private EntityParameter id;

    private List<EntityParameter> parameters = new ArrayList<>();

    public SimpleEntity(@NotNull final Object parent){
        this.parent = parent;

        this.init();
    }

    private void init(){
        final Class<?> clazz = this.parent.getClass();

        if (!clazz.isAnnotationPresent(Entity.class))
            return;

        for (final Field field : clazz.getDeclaredFields()){
            if (field.getName().contains("this"))
                continue;

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
            }

            this.parameters.add(parameter);

            field.setAccessible(false);
        }
    }

}
