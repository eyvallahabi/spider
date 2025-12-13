package io.github.shiryu.spider.requirement.events.death;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "victim_is_player", controls = EntityDeathEvent.class)
public class VictimIsPlayerRequirement implements Requirement<EntityDeathEvent> {

    public VictimIsPlayerRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDeathEvent controlled) {
        return controlled.getEntity() instanceof Player;
    }
}
