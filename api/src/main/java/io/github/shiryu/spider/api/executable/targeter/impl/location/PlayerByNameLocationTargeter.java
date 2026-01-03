package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@player_by_name_location", aliases = "@pnl", description = "Targets a player's location by their name")
public class PlayerByNameLocationTargeter implements Targeter<Location> {

    private String name;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.name = context.targeter().getOrDefault("name", "");
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        if (this.name == null || this.name.isEmpty())
            return null;

        final var player = context.getCaster().getServer().getPlayer(this.name);

        if (player == null)
            return Lists.newArrayList();

        return List.of(player.getLocation());
    }
}
