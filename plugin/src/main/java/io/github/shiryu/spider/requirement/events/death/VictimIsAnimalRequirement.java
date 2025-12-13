package io.github.shiryu.spider.requirement.events.death;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Animals;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "victim_is_animal", controls = EntityDeathEvent.class)
public class VictimIsAnimalRequirement implements Requirement<EntityDeathEvent> {

    public VictimIsAnimalRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDeathEvent controlled) {
        return controlled.getEntity() instanceof Animals;
    }
}
