package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

@TriggerInfo(name = "@death", description = "Triggered when an entity dies.")
public class DeathTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void death(final EntityDeathEvent event){
        final Entity entity = event.getEntity();

        call(
                ExecutionContextBuilder.newBuilder()
                        .caster(entity)
                        .trigger(entity)
                        .location(entity.getLocation())
                        .world(entity.getWorld())
                        .event(event)
                        .integer("droppedExp", event.getDroppedExp())
                        .list("drops", event.getDrops())
                        .decimal("reviveHealth", event.getReviveHealth())
                        .build()
        );
    }
}
