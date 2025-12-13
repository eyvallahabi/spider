package io.github.shiryu.spider.requirement.events.damage;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "damager_is_player", controls = EntityDamageByEntityEvent.class)
public class DamagerIsPlayerRequirement implements Requirement<EntityDamageByEntityEvent> {

    public DamagerIsPlayerRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDamageByEntityEvent controlled) {
        return controlled.getDamager() instanceof Player;
    }
}
