package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@highest_block", description = "Targets the highest block at a location")
public class HighestBlockTargeter implements Targeter<Location> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location location = context.get("location");

        if (location == null)
            return null;

        final Location highest = location.getWorld().getHighestBlockAt(location).getLocation();

        highest.setYaw(location.getYaw());
        highest.setPitch(location.getPitch());

        return highest;
    }
}
