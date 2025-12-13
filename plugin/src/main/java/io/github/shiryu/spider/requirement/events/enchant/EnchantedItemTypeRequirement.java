package io.github.shiryu.spider.requirement.events.enchant;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.Material;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

@RequirementInfo(name = "enchanted_item_type", controls = EnchantItemEvent.class)
public class EnchantedItemTypeRequirement implements Requirement<EnchantItemEvent> {

    private final List<Material> materials;

    public EnchantedItemTypeRequirement(@NotNull final String... args){
        this.materials = Stream.of(args)
                .map(Material::valueOf)
                .toList();
    }

    @Override
    public boolean control(@NotNull EnchantItemEvent controlled) {
        return this.materials.contains(controlled.getItem().getType());
    }
}
