package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@players_in_world", aliases = "@piw", description = "Targets all players in the world of the caster")
public class PlayersInWorldTargeter implements MultiTargeter<Player> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.caster();

        return caster.getWorld().getPlayers();
    }
}
