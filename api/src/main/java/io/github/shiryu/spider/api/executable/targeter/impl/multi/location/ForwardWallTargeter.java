package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Parse(name = "@forwardwall", description = "Targets locations in a forward wall pattern")
public class ForwardWallTargeter implements MultiTargeter<Location> {

    private int distance,height,width;
    private double yOffset;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.distance = Integer.parseInt(context.targeter().getOrDefault("distance", "5"));
        this.height = Integer.parseInt(context.targeter().getOrDefault("height", "3"));
        this.width = Integer.parseInt(context.targeter().getOrDefault("width", "5"));
        this.yOffset = Double.parseDouble(context.targeter().getOrDefault("y_offset", "0.0"));
    }

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return null;

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
