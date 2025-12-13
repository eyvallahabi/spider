package io.github.shiryu.spider.requirement.events.breeding;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityBreedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

@RequirementInfo(name = "child_type", controls = EntityBreedEvent.class)
public class ChildTypeRequirement implements Requirement<EntityBreedEvent> {

    private final List<EntityType> types;

    public ChildTypeRequirement(@NotNull final String... args){
        this.types = Stream.of(args)
                .map(EntityType::valueOf)
                .toList();
    }

    @Override
    public boolean control(@NotNull EntityBreedEvent controlled) {
        return this.types.contains(controlled.getEntity().getType());
    }
}
