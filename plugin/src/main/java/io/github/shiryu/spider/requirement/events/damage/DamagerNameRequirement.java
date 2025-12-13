package io.github.shiryu.spider.requirement.events.damage;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequirementInfo(name = "damager_name", controls = EntityDamageByEntityEvent.class)
public class DamagerNameRequirement implements Requirement<EntityDamageByEntityEvent> {

    private final List<String> names;

    public DamagerNameRequirement(@NotNull final String... args){
        this.names = List.of(args);
    }

    @Override
    public boolean control(@NotNull EntityDamageByEntityEvent controlled) {
        if (!(controlled.getDamager() instanceof Player damager))
            return false;

        return this.names.contains(damager.getName());
    }
}
