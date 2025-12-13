package io.github.shiryu.spider.requirement.events.damage;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Animals;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "damaged_is_animal", controls = EntityDamageByEntityEvent.class)
public class DamagedIsAnimalRequirement implements Requirement<EntityDamageByEntityEvent> {

    public DamagedIsAnimalRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDamageByEntityEvent controlled) {
        return controlled.getEntity() instanceof Animals;
    }
}
