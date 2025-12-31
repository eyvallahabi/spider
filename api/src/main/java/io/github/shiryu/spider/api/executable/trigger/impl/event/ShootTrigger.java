package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;

@Parse(name = "@shoot", description = "Triggered when an entity shoots a projectile")
public class ShootTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void shoot(final ProjectileLaunchEvent event){
        final Projectile projectile = event.getEntity();
        final Entity shooter = projectile.getShooter() instanceof Entity ? (Entity) projectile.getShooter() : null;

        if (shooter == null)
            return;

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(shooter)
                        .trigger(projectile)
                        .entity("projectile", projectile)
                        .event(event)
                        .location(shooter.getLocation())
                        .world(shooter.getWorld())
                        .build()
        );
    }
}
