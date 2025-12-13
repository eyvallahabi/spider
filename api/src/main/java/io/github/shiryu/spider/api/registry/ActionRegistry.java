package io.github.shiryu.spider.api.registry;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.control.controller.ControllerHolder;
import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.api.control.controller.holder.ActionHolder;
import io.github.shiryu.spider.api.control.controller.ControllerRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;

public class ActionRegistry implements ControllerRegistry<Action> {

    private final List<ActionHolder> holders = Lists.newArrayList();

    @Override
    public void register(@NotNull Class<?> clazz) {
        final var info = clazz.getAnnotation(ActionInfo.class);
        if (info == null)
            throw new IllegalArgumentException("Action " + clazz.getName() + " is missing ActionInfo annotation");

        holders.add(new ActionHolder(
                clazz,
                info.name()
        ));
    }

    @Override
    public @Nullable ControllerHolder find(@NotNull String id) {
        return holders.stream()
                .filter(holder -> holder.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable Action create(@NotNull String raw) {
        final String id = raw.split(" ")[0];
        final String[] args = raw.substring(id.length()).trim().split(",");

        final ActionHolder holder = (ActionHolder) find(id);

        if (holder == null)
            throw new IllegalArgumentException("Action " + id + " is missing ActionInfo annotation");

        try{
            final Constructor<?> constructor = holder.getClazz().getConstructor(String[].class);

            return (Action) constructor.newInstance((Object)args);
        }catch (final Exception exception){
            throw new IllegalArgumentException(exception);
        }
    }

}
