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
import java.util.UUID;

@Parse(name = "@player_with_uuid", aliases = "@pwuid", description = "Targets a player by UUID")
public class PlayerWithUUID implements Targeter<Player> {

    private UUID uuid;

    @Override
    public void initialize(@NotNull ParseContext context) {
        final String text = context.targeter().get("uuid");

        if (text == null)
            return;

        try{
            this.uuid = UUID.fromString(text);
        }catch (final Exception exception){
            throw new IllegalArgumentException("Invalid UUID format: " + text);
        }
    }

    @Override
    public @NonNull List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        if (this.uuid == null)
            return Lists.newArrayList();

        final Player player = Bukkit.getPlayer(this.uuid);

        return player != null ? List.of(player) : Lists.newArrayList();
    }
}
