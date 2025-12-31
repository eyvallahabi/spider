package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@Parse(name = "@interact", description = "Triggered when a player interacts with a block or entity")
public class InteractTrigger implements EventTrigger {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void interact(final PlayerInteractEvent event){
        final Player player = event.getPlayer();

        ExecutionContextBuilder builder = ExecutionContextBuilder.newBuilder()
                .caster(player)
                .event(event)
                .location(player.getLocation())
                .world(player.getWorld());

        if (event.hasBlock() && event.getClickedBlock() != null)
            builder = builder.block("clickedBlock", event.getClickedBlock())
                    .trigger(event.getClickedBlock());

        if (event.hasItem() && event.getItem() != null)
            builder = builder.item("itemInHand", event.getItem());

        this.call(builder.build());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void interactEntity(final PlayerInteractAtEntityEvent event){
        final Player player = event.getPlayer();

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(player)
                        .trigger(event.getRightClicked())
                        .entity("rightClickedEntity", event.getRightClicked())
                        .event(event)
                        .location(player.getLocation())
                        .world(player.getWorld())
                        .build()
        );
    }
}
