package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@cone", description = "Targets locations in a cone shape")
public class ConeTargeter implements Targeter<Location> {

    private int maxDistance;
    private double angle,yOffset,step;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.maxDistance = Integer.parseInt(context.targeter().getOrDefault("distance", "10"));
        this.angle = Double.parseDouble(context.targeter().getOrDefault("angle", "45.0"));
        this.yOffset = Double.parseDouble(context.targeter().getOrDefault("y_offset", "0.0"));
        this.step = Double.parseDouble(context.targeter().getOrDefault("step", "0.5"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {

        final Location origin = context.get("location");

        if (origin == null)
            return Lists.newArrayList();

        return SpiderLocation.from(origin)
                .cone(maxDistance, angle, yOffset, step)
                .stream()
                .map(SpiderLocation::convert)
                .toList();
    }
}
