package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@location", aliases = {"@l", "@loc"}, description = "Targets a known location from the context")
public class KnownLocationTargeter implements Targeter<Location> {

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return new Location(
                context.get("world"),
                context.getOrSet("x", 0.0),
                context.getOrSet("y",  0.0),
                context.getOrSet("z", 0.0),
                context.getOrSet("yaw", 0.0f),
                context.getOrSet("pitch", 0.0f)
        );
    }
}
