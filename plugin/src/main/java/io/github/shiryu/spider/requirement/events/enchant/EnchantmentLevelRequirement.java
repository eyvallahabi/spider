package io.github.shiryu.spider.requirement.events.enchant;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "enchantment_level", controls = EnchantItemEvent.class)
public class EnchantmentLevelRequirement implements Requirement<EnchantItemEvent> {

    private final Enchantment enchantment;
    private final int level;

    public EnchantmentLevelRequirement(@NotNull final String... args){
        this.enchantment = Enchantment.getByName(args[0]);
        this.level = Integer.parseInt(args[1]);
    }

    @Override
    public boolean control(@NotNull EnchantItemEvent controlled) {
        return controlled.getEnchantsToAdd().getOrDefault(this.enchantment, 0) == this.level;
    }
}
