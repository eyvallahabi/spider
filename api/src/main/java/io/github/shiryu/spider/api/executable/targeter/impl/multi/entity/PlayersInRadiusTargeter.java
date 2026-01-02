package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@players_in_radius", aliases = {"@pir", "@players_nearby"}, description = "Targets all players within a certain radius")
public class PlayersInRadiusTargeter implements MultiTargeter<Player> {

    private double radius;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
    }

    @Override
    public @Nullable List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.caster()
                .getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .toList();
    }
}
