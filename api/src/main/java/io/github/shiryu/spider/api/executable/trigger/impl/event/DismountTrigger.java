package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDismountEvent;
import org.jetbrains.annotations.NotNull;

@Parse(name = "@dismount", description = "Triggered when an entity dismounts from another entity")
public class DismountTrigger implements EventTrigger {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void dismount(final EntityDismountEvent event){
        final Entity entity = event.getEntity();
        final Entity dismounted = event.getDismounted();

        this.call(
                ExecutionContextBuilder.newBuilder()
                        .caster(entity)
                        .trigger(dismounted)
                        .location(entity.getLocation())
                        .world(entity.getWorld())
                        .event(event)
                        .build()
        );
    }
}
