package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTameEvent;
import org.jetbrains.annotations.NotNull;

@Parse(name = "@tame", description = "Triggered when an entity is tamed")
public class TameTrigger implements EventTrigger {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

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
