package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Parse(name = "@player_with_uuid", aliases = "@pwuid", description = "Targets a player by UUID")
public class PlayerWithUUID implements Targeter<Player> {

    @Override
    public @Nullable Player find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final UUID uuid;

        try{
            final String text = context.get("target_player_uuid");

            if (text == null)
                return null;

            uuid = UUID.fromString(text);
        } catch (IllegalArgumentException e){
            return null;
        }

        return Bukkit.getPlayer(uuid);
    }
}
