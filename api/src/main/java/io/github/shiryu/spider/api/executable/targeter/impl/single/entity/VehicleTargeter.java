package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@vehicle", description = "Targets the entity the caster's vehicle")
public class VehicleTargeter implements Targeter<Entity> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable Entity find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.caster();

        return caster.getVehicle();
    }
}
