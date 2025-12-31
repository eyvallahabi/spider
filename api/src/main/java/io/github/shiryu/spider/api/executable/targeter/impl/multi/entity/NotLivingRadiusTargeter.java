package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@not_living_radius", aliases = {"@nli"}, description = "Targets all non-living entities within a radius")
public class NotLivingRadiusTargeter implements MultiTargeter<Entity> {

    @Override
    public @Nullable List<Entity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final double radius = context.getOrSet("radius", 5.0);

        return context.caster()
                .getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> !(entity instanceof LivingEntity))
                .toList();
    }
}
