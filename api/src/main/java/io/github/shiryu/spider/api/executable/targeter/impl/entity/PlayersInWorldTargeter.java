package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@players_in_world", aliases = "@piw", description = "Targets all players in the world of the caster")
public class PlayersInWorldTargeter implements Targeter<Player> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.getCaster();

        return caster.getWorld().getPlayers();
    }
}
