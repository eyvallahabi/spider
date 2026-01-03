package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@player_with_name", aliases = "@pwn", description = "Targets a player by name")
public class PlayerWithNameTargeter implements Targeter<Player> {

    private String name;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.name = context.targeter().get("name");
    }

    @Override
    public @NonNull List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        if (this.name == null)
            return Lists.newArrayList();

        final Player player = Bukkit.getOnlinePlayers()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(this.name))
                .findFirst()
                .orElse(null);

        return player != null ? List.of(player) : Lists.newArrayList();
    }
}
