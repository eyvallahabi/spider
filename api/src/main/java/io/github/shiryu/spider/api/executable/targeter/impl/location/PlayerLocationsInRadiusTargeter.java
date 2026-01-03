package io.github.shiryu.spider.api.executable.targeter.impl.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@playerlocationsinradius", aliases = "@plir", description = "Targets all player locations within a specified radius")
public class PlayerLocationsInRadiusTargeter implements Targeter<Location> {

    private double radius;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final var caster = context.getCaster();

        return caster.getLocation().getNearbyPlayers(radius)
                .stream()
                .map(Player::getLocation)
                .toList();
    }
}
