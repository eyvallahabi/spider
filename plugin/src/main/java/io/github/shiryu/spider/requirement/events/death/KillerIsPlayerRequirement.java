package io.github.shiryu.spider.requirement.events.death;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "killer_is_player", controls = EntityDeathEvent.class)
public class KillerIsPlayerRequirement implements Requirement<EntityDeathEvent> {

    public KillerIsPlayerRequirement(@NotNull final String... args){

    }

    @Override
    public boolean control(@NotNull EntityDeathEvent controlled) {
        return controlled.getEntity().getKiller() != null;
    }
}
