package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Parse(name = "@forwardwall", description = "Targets locations in a forward wall pattern")
public class ForwardWallTargeter implements MultiTargeter<Location> {

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return null;

        final int distance = context.getOrSet("distance", 5);
        final int height = context.getOrSet("height", 3);
        final int width = context.getOrSet("width", 5);
        final double yOffset = context.getOrSet("y_offset", 0.0);

        final Vector forward = origin.getDirection().normalize();

        final Vector right = forward.clone().crossProduct(new Vector(0, 1, 0)).normalize();

        final Vector up = new Vector(0, 1, 0);

        final Location center = origin.clone().add(0, yOffset, 0).add(forward.clone().multiply(distance));

        final List<Location> results = new ArrayList<>();

        int half = width / 2;

        for (int y = 0; y < height; y++) {
            for (int x = -half; x <= half; x++) {
                Location loc = center.clone()
                        .add(right.clone().multiply(x))
                        .add(up.clone().multiply(y));
                results.add(loc);
            }
        }

        return results;

    }

}
