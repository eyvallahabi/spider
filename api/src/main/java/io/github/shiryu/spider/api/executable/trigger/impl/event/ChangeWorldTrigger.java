package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTeleportEvent;

@Parse(name = "@changeworld", description = "Triggered when an entity changes world")
public class ChangeWorldTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void change(final EntityTeleportEvent event){
        final Entity entity = event.getEntity();

        final Location from = event.getFrom();
        final Location to = event.getTo();

        if (to == null)
            return;

        if (from.getWorld() == to.getWorld())
            return;

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(entity)
                        .trigger(to)
                        .location("from", from)
                        .location("to", to)
                        .world(entity.getWorld())
                        .location(entity.getLocation())
                        .build()
        );
    }
}
