package io.github.shiryu.spider.requirement.events.death;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequirementInfo(name = "victim_type", controls = EntityDeathEvent.class)
public class VictimTypeRequirement implements Requirement<EntityDeathEvent> {

    private final List<EntityType> types;

    public VictimTypeRequirement(@NotNull final String... args){
        final List<String> names = List.of(args);

        this.types = names.stream()
                .map(String::toUpperCase)
                .map(EntityType::valueOf)
                .toList();
    }

    @Override
    public boolean control(@NotNull EntityDeathEvent controlled) {
        return this.types.contains(controlled.getEntityType());
    }
}
