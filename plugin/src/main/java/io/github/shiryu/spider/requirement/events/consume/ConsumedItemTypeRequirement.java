package io.github.shiryu.spider.requirement.events.consume;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

@RequirementInfo(name = "consumed_item_type", controls = PlayerItemConsumeEvent.class)
public class ConsumedItemTypeRequirement implements Requirement<PlayerItemConsumeEvent> {

    private final List<Material> materials;

    public ConsumedItemTypeRequirement(@NotNull final String... args){
        this.materials = Stream.of(args)
                .map(Material::valueOf)
                .toList();
    }

    @Override
    public boolean control(@NotNull PlayerItemConsumeEvent controlled) {
        final ItemStack item = controlled.getItem();

        return materials.contains(item.getType());
    }
}
