package io.github.shiryu.spider.requirement.events.crafting;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

@RequirementInfo(name = "crafted_item_type", controls = CraftItemEvent.class)
public class CraftedItemTypeRequirement implements Requirement<CraftItemEvent> {

    private final List<Material> materials;

    public CraftedItemTypeRequirement(@NotNull final String... args){
        this.materials = Stream.of(args)
                .map(Material::valueOf)
                .toList();
    }

    @Override
    public boolean control(@NotNull CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return false;

        if (event.getCurrentItem() == null)
            return false;

        return materials.contains(event.getCurrentItem().getType());
    }
}
