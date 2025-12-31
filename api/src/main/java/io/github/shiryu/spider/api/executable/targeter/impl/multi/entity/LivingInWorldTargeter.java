package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@living_in_world", aliases = {"@liw", "@living_entities_in_world", "@livings_in_world", "@livings_in_world"}, description = "Targets all living entities in the world of the caster")
public class LivingInWorldTargeter implements MultiTargeter<LivingEntity> {

    @Override
    public @Nullable List<LivingEntity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.caster()
                .getWorld()
                .getLivingEntities();
    }
}
