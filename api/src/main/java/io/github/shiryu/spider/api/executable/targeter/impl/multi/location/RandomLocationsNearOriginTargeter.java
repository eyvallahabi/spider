package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@random_locations_near_origin", description = "Targets random locations near the origin location")
public class RandomLocationsNearOriginTargeter implements MultiTargeter<Location> {

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return null;

        final double radius = context.getOrSet("radius", 5.0);
        final int amount = context.getOrSet("amount", 5);

        return SpiderLocation.from(origin).randomLocationsNear(radius, amount)
                .stream().map(SpiderLocation::convert).toList();
    }
}
