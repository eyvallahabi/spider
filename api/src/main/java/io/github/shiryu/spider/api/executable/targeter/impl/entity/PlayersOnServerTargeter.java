package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@players_on_server", aliases = "@pos", description = "Targets all players on the server")
public class PlayersOnServerTargeter implements Targeter<Player> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return Lists.newArrayList(context.getCaster().getServer().getOnlinePlayers());
    }
}
