package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@target_block", aliases = "@tb", description = "Targets the block location of the target")
public class TargetBlockTargeter implements Targeter<Location> {

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final int maxDistance = context.getOrSet("max_distance", 64);

        Location origin;

        if (!(context.caster() instanceof LivingEntity living))
            origin = context.caster().getLocation();
        else
            origin = living.getEyeLocation();

        final RayTraceResult result = context.caster().getWorld().rayTraceBlocks(
                origin,
                origin.getDirection(),
                maxDistance,
                FluidCollisionMode.NEVER
        );

        if (result == null)
            return null;

        final Block hit = result.getHitBlock();

        if (hit == null)
            return null;

        return hit.getLocation();
    }
}
