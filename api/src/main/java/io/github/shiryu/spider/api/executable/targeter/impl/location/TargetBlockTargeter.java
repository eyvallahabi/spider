package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@target_block", aliases = "@tb", description = "Targets the block location of the target")
public class TargetBlockTargeter implements Targeter<Location> {

    private int maxDistance;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.maxDistance = Integer.parseInt(context.targeter().getOrDefault("distance", "64"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {

        Location origin;

        if (!(context.getCaster() instanceof LivingEntity living))
            origin = context.getCaster().getLocation();
        else
            origin = living.getEyeLocation();

        final RayTraceResult result = context.getCaster().getWorld().rayTraceBlocks(
                origin,
                origin.getDirection(),
                this.maxDistance,
                FluidCollisionMode.NEVER
        );

        if (result == null)
            return Lists.newArrayList();

        final Block hit = result.getHitBlock();

        if (hit == null)
            return Lists.newArrayList();

        return List.of(hit.getLocation());
    }
}
