package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@players_on_server", aliases = "@pos", description = "Targets all players on the server")
public class PlayersOnServerTargeter implements MultiTargeter<Player> {

    @Override
    public @Nullable List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return Lists.newArrayList(context.caster().getServer().getOnlinePlayers());
    }
}
