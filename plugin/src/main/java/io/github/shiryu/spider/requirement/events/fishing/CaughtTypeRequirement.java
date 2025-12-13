package io.github.shiryu.spider.requirement.events.fishing;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

@RequirementInfo(name = "caught_type", controls = PlayerFishEvent.class)
public class CaughtTypeRequirement implements Requirement<PlayerFishEvent> {

    private final List<Material> materials;

    public CaughtTypeRequirement(@NotNull final String... args){
        this.materials = Stream.of(args)
                .map(Material::valueOf)
                .toList();
    }

    @Override
    public boolean control(@NotNull PlayerFishEvent controlled) {
        final Entity caught = controlled.getCaught();

        if (caught == null)
            return false;

        if (!(caught instanceof Item item))
            return false;

        return this.materials.contains(item.getItemStack().getType());
    }
}
