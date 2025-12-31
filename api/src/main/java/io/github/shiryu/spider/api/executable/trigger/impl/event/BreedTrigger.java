package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

@TriggerInfo(name = "@breed", description = "Triggers when two animals breed.")
public class BreedTrigger implements EventTrigger {

    @EventHandler
    public void breed(final EntityBreedEvent event){
        final Entity entity = event.getEntity();
        final Entity breeder = event.getBreeder();

        if (breeder == null)
            return;

        final ItemStack item = event.getBredWith();

        final Entity father = event.getFather();
        final Entity mother = event.getMother();
        final int experience = event.getExperience();

        var builder = ExecutionContextBuilder.newBuilder()
                .caster(breeder)
                .trigger(entity)
                .location(breeder.getLocation())
                .world(breeder.getWorld())
                .integer("experience", event.getExperience())
                .entity("father", event.getFather())
                .entity("mother", event.getMother());

        if (item != null)
            builder = builder.item("bredWith", item);

        this.call(builder.build());
    }
}
