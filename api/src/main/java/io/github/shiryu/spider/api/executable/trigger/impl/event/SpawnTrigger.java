package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;

@Parse(name = "@spawn", description = "Triggered when an entity spawns")
public class SpawnTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void spawn(final EntitySpawnEvent event){
        final Entity entity = event.getEntity();

        call(
                ExecutionContextBuilder.newBuilder()
                        .caster(entity)
                        .trigger(entity)
                        .event(event)
                        .location(entity.getLocation())
                        .world(entity.getWorld())
                        .string("entityType", entity.getType().name())
                        .build()
        );
    }
}
