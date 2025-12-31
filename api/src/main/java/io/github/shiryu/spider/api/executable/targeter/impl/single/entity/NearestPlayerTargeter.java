package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@nearest_player", aliases = "@np", description = "Targets the nearest player")
public class NearestPlayerTargeter implements Targeter<Player> {

    @Override
    public @Nullable Player find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location center = context.get("location");
        final double radius = context.getOrSet("radius", 10.0);

        if (center == null)
            return null;

        Player nearest = null;

        double nearestDistanceSquared = Double.MAX_VALUE;

        for (final Player player : center.getWorld().getPlayers()) {
            final double distanceSquared = player.getLocation().distanceSquared(center);

            if (distanceSquared <= radius * radius && distanceSquared < nearestDistanceSquared) {
                nearest = player;
                nearestDistanceSquared = distanceSquared;
            }
        }

        return nearest;
    }
}
