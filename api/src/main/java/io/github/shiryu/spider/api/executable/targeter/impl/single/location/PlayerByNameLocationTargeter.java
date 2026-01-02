package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@player_by_name_location", aliases = "@pnl", description = "Targets a player's location by their name")
public class PlayerByNameLocationTargeter implements Targeter<Location> {

    private String name;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.name = context.targeter().getOrDefault("name", "");
    }

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        if (this.name == null || this.name.isEmpty())
            return null;

        final var player = context.caster().getServer().getPlayer(this.name);

        if (player == null)
            return null;

        return player.getLocation();
    }
}
