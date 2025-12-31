package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

@TriggerInfo(name = "@trade", description = "Triggers when a player trades with a villager.")
public class TradeTrigger implements EventTrigger {

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
