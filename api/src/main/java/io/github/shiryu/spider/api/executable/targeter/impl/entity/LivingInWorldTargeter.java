package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@living_in_world", aliases = {"@liw", "@living_entities_in_world", "@livings_in_world", "@livings_in_world"}, description = "Targets all living entities in the world of the caster")
public class LivingInWorldTargeter implements Targeter<Entity> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Entity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.getCaster()
                .getWorld()
                .getLivingEntities()
                .stream()
                .map(living -> (Entity) living)
                .toList();
    }
}
