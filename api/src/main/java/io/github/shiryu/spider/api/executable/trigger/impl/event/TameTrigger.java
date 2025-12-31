package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTameEvent;

@TriggerInfo(name = "@tame", description = "Triggers when a player tames an entity.")
public class TameTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void tame(final EntityTameEvent event){
        final var owner = event.getOwner() instanceof Entity ? (Entity) event.getOwner() : null;
        final var tamed = event.getEntity();

        if (owner == null)
            return;

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(owner)
                        .trigger(tamed)
                        .entity("tamed", tamed)
                        .location(owner.getLocation())
                        .world(owner.getWorld())
                        .event(event)
                        .build()
        );
    }
}
