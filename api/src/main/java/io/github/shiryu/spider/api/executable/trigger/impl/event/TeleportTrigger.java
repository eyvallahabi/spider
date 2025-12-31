package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTeleportEvent;

@Parse(name = "@teleport", description = "Triggered when an entity teleports")
public class TeleportTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void teleport(final EntityTeleportEvent event){
        final Entity entity = event.getEntity();

        final Location from = event.getFrom();
        final Location to = event.getTo();

        final ExecutionContextBuilder builder = ExecutionContextBuilder.newBuilder()
                .caster(entity)
                .trigger(entity)
                .location("from", from)
                .event(event);

        if (to == null){
            this.call(builder.build());
            return;
        }

        this.call(
                builder.location("to", to).build()
        );
    }
}
