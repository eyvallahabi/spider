package io.github.shiryu.spider.api.control;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Controllable {

    @NotNull
    List<Requirement> getRequirements();

    @NotNull
    List<Action> getActions();

    default boolean control(@NotNull final Object... args){

        boolean accepted = true;

        for (final Object arg : args){
            Class<?> search = arg.getClass();

            if (search.getSimpleName().equals("CraftPlayer"))
                search = Player.class;
            else if (search.getSimpleName().equals("CraftItemStack"))
                search = ItemStack.class;

            final List<Requirement> requirements = this.requirementsOf(search);

            if (requirements.isEmpty())
                continue;

            boolean argAccepted = true;

            for (final var requirement : requirements){
                argAccepted = requirement.control(arg);
            }

            accepted = accepted && argAccepted;
        }

        return accepted;
    }

    @NotNull
    default List<Requirement> requirementsOf(@NotNull final Class<?> clazz){
        return this.getRequirements().stream()
                .filter(requirement -> {
                    final RequirementInfo info = requirement.getClass().getAnnotation(RequirementInfo.class);
                    return info != null && info.controls().equals(clazz);
                })
                .toList();
    }

}
