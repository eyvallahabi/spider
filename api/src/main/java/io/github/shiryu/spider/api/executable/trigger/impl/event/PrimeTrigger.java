package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ExplosionPrimeEvent;

@Parse(name = "@prime", description = "Triggered when an entity primes an explosion")
public class PrimeTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void prime(final ExplosionPrimeEvent event){
        final Entity entity = event.getEntity();

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(entity)
                        .trigger(entity)
                        .location(entity.getLocation())
                        .world(entity.getWorld())
                        .event(event)
                        .decimal("radius", event.getRadius())
                        .bool("fire", event.getFire())
                        .build()
        );
    }
}
