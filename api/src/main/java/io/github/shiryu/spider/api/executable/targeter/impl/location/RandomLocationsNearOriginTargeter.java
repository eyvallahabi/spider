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

@Parse(name = "@random_locations_near_origin", description = "Targets random locations near the origin location")
public class RandomLocationsNearOriginTargeter implements Targeter<Location> {

    private double radius;
    private int amount;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
        this.amount = Integer.parseInt(context.targeter().getOrDefault("amount", "5"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return Lists.newArrayList();

        return SpiderLocation.from(origin).randomLocationsNear(radius, amount)
                .stream().map(SpiderLocation::convert).toList();
    }
}
