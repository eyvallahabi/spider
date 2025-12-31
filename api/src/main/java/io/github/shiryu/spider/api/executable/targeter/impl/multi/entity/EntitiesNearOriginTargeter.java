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

@Parse(name = "@entities_near_origin", aliases = "@eno", description = "Targets all entities near the origin point")
public class EntitiesNearOriginTargeter implements MultiTargeter<Entity> {

    @Override
    public @Nullable List<Entity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.caster();
        final double radius = context.getOrSet("radius", 5.0);

        final boolean living = context.getOrSet("living", false);

        if (living){
            return caster.getNearbyEntities(radius, radius, radius)
                    .stream()
                    .filter(entity -> entity instanceof LivingEntity)
                    .toList();
        }

        return caster.getNearbyEntities(radius, radius, radius);
    }
}
