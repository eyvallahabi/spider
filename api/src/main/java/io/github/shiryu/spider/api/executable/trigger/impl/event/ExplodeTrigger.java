package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

@Parse(name = "@explode", description = "Triggered when an entity explodes")
public class ExplodeTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void explode(final EntityExplodeEvent event){
        final Entity entity = event.getEntity();

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(entity)
                        .trigger(entity)
                        .location(entity.getLocation())
                        .world(entity.getWorld())
                        .event(event)
                        .location("explosionLocation", event.getLocation())
                        .decimal("yield", event.getYield())
                        .build()
        );
    }
}
