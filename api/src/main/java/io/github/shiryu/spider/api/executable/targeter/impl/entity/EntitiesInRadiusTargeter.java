package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@entities_in_radius", aliases = "@eir", description = "Targets all entities within a certain radius")
public class EntitiesInRadiusTargeter implements Targeter<Entity> {

    private double radius;
    private boolean living;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
        this.living = Boolean.parseBoolean(context.targeter().getOrDefault("living", "false"));
    }

    @Override
    public @NonNull List<Entity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.getCaster();

        if (living){
            return caster.getNearbyEntities(radius, radius, radius)
                    .stream()
                    .filter(entity -> entity instanceof LivingEntity)
                    .toList();
        }

        return caster.getNearbyEntities(radius, radius, radius);
    }
}
