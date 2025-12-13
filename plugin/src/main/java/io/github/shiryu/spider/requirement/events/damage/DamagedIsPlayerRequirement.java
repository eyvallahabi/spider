package io.github.shiryu.spider.requirement.events.damage;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "damaged_is_player", controls = EntityDamageByEntityEvent.class)
public class DamagedIsPlayerRequirement implements Requirement<EntityDamageByEntityEvent> {

    public DamagedIsPlayerRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDamageByEntityEvent controlled) {
        return controlled.getEntity() instanceof Player;
    }
}
