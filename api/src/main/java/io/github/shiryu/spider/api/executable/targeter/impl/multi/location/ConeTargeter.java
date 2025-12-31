package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@cone", description = "Targets locations in a cone shape")
public class ConeTargeter implements MultiTargeter<Location> {

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {

        final Location origin = context.get("location");

        if (origin == null)
            return null;

        final int maxDistance = context.getOrSet("distance", 10);
        final double angle = Math.toRadians(context.getOrSet("angle", 45.0));
        final double yOffset = context.getOrSet("y_offset", 0.0);
        final double step = context.getOrSet("step", 0.5);

        return SpiderLocation.from(origin)
                .cone(maxDistance, angle, yOffset, step)
                .stream()
                .map(SpiderLocation::convert)
                .toList();
    }
}
