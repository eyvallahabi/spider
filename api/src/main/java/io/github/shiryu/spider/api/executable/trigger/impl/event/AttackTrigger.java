package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@TriggerInfo(name = "@attack", description = "Triggered when an entity attacks another entity.")
public class AttackTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void attack(final EntityDamageByEntityEvent event){
        final Entity victim = event.getEntity();
        final Entity damager = event.getDamager();

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(damager)
                        .entity("damaged", victim)
                        .event(event)
                        .location(damager.getLocation())
                        .world(damager.getWorld())
                        .decimal("damage", event.getDamage())
                        .decimal("finalDamage", event.getFinalDamage())
                        .string("cause", event.getCause().name())
                        .build()
        );
    }
}
