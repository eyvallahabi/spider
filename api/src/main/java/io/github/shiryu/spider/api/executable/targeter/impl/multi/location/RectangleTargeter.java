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

@Parse(name = "@rectangle", description = "Targets locations in a rectangle")
public class RectangleTargeter implements MultiTargeter<Location> {

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.caster().getLocation();
        final double length = context.getOrSet("length", 5.0);
        final double width = context.getOrSet("width", 5.0);
        final double step = context.getOrSet("step", 1.0);

        return SpiderLocation.from(origin).rectangle(length, width, step)
                .stream().map(SpiderLocation::convert).toList();
    }
}
