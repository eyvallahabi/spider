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

@Parse(name = "@not_living_radius", aliases = {"@nli"}, description = "Targets all non-living entities within a radius")
public class NotLivingRadiusTargeter implements Targeter<Entity> {

    private double radius;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
    }

    @Override
    public @NonNull List<Entity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.getCaster()
                .getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> !(entity instanceof LivingEntity))
                .toList();
    }
}
