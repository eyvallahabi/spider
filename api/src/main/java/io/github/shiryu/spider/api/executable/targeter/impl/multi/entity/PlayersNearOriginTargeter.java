package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@players_near_origin", aliases = "@pno", description = "Targets all players near the origin")
public class PlayersNearOriginTargeter implements MultiTargeter<Player> {

    @Override
    public @Nullable List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return null;

        final double radius = context.getOrSet("radius", 10.0);

        return origin.getWorld().getPlayers().stream()
                .filter(player -> player.getLocation().distanceSquared(origin) <= radius * radius)
                .toList();
    }
}
