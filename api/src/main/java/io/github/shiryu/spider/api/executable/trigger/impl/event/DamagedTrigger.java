package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@Parse(name = "@damaged", description = "Triggered when an entity takes damage")
public class DamagedTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void byEntity(final EntityDamageByEntityEvent event){
        final Entity victim = event.getEntity();
        final Entity damager = event.getDamager();

        this.call(
                ExecutionContextBuilder
                        .newBuilder()
                        .caster(victim)
                        .event(event)
                        .location(victim.getLocation())
                        .world(victim.getWorld())
                        .trigger(damager)
                        .entity("damager", damager)
                        .decimal("damage", event.getDamage())
                        .decimal("finalDamage", event.getFinalDamage())
                        .string("cause", event.getCause().name())
                        .build()
        );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void damage(final EntityDamageEvent event){
        final Entity entity = event.getEntity();

        this.call(
                ExecutionContextBuilder
                        .newBuilder()
                        .caster(entity)
                        .event(event)
                        .trigger(entity)
                        .location(entity.getLocation())
                        .world(entity.getWorld())
                        .decimal("damage", event.getDamage())
                        .decimal("finalDamage", event.getFinalDamage())
                        .string("cause", event.getCause().name())
                        .build()
        );
    }
}
