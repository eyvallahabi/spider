package io.github.shiryu.spider.api.registry;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.control.controller.ControllerHolder;
import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import io.github.shiryu.spider.api.control.controller.holder.RequirementHolder;
import io.github.shiryu.spider.api.control.controller.ControllerRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;

public class RequirementRegistry implements ControllerRegistry<Requirement> {

    private final List<RequirementHolder> holders = Lists.newArrayList();

    @Override
    public void register(@NotNull Class<?> clazz) {
        final RequirementInfo info = clazz.getAnnotation(RequirementInfo.class);

        if (info == null)
            throw new IllegalArgumentException("Requirement " + clazz.getName() + " is missing RequirementInfo annotation");

        holders.add(new RequirementHolder(
                clazz,
                info.name(),
                info.controls()
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
    public @Nullable Requirement create(@NotNull String raw) {
        final String id = raw.split(" ")[0];

        final String[] args = raw.substring(id.length()).trim().split(",");

        final RequirementHolder holder = (RequirementHolder) find(id);

        if (holder == null)
            return null;

        try{
            final Constructor<?> constructor = holder.getClazz().getConstructor(String[].class);

            return (Requirement) constructor.newInstance((Object) args);
        }catch (final Exception exception){
            throw new IllegalArgumentException(exception);
        }
    }
}
