package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class BlocksNearOriginTargeter implements MultiTargeter<Location> {

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return null;

        final double radius = context.getOrSet("radius", 5.0);
        final double radiusY = context.getOrSet("radius_y", radius);

        final String shape = context.getOrSet("shape", "sphere").toLowerCase();

        final double noise = context.getOrSet("noise", 0.0);
        final boolean noAir = context.getOrSet("no_air", false);
        final boolean onlyAir = context.getOrSet("only_air", false);

        return switch (shape) {
            case "sphere" -> SpiderLocation.from(origin).blocksInSphere(
                    radius,
                    radiusY,
                    noise,
                    noAir,
                    onlyAir
            ).stream().map(SpiderLocation::convert).toList();
            case "cube" -> SpiderLocation.from(origin).blocksInCube(
                    radius,
                    radiusY,
                    noise,
                    noAir,
                    onlyAir
            ).stream().map(SpiderLocation::convert).toList();
            default -> Lists.newArrayList();
        };
    }
}
