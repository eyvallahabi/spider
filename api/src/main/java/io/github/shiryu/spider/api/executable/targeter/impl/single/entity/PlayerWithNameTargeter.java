package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@player_with_name", aliases = "@pwn", description = "Targets a player by name")
public class PlayerWithNameTargeter implements Targeter<Player> {

    @Override
    public @Nullable Player find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final String name = context.get("target_player_name");

        if (name == null)
            return null;

        return Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> player.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
