package io.github.shiryu.spider.requirement.events.damage;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import io.github.shiryu.spider.util.EntityUtil;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "damaged_is_monster", controls = EntityDamageByEntityEvent.class)
public class DamagedIsMonsterRequirement implements Requirement<EntityDamageByEntityEvent> {

    public DamagedIsMonsterRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDamageByEntityEvent controlled) {
        return EntityUtil.isMonster(controlled.getEntity());
    }
}
