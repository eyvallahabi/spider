package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

@Parse(name = "@trade", description = "Triggered when a player trades with a villager")
public class TradeTrigger implements EventTrigger {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void trade(final PlayerTradeEvent event){
        final Player player = event.getPlayer();

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(player)
                        .trigger(event.getVillager())
                        .world(player.getWorld())
                        .location(player.getLocation())
                        .build()
        );
    }
}
