package io.github.shiryu.spider.requirement.events.enchant;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "enchantment_type", controls = EnchantItemEvent.class)
public class EnchantmentTypeRequirement implements Requirement<EnchantItemEvent> {

    private final Enchantment enchantment;

    public EnchantmentTypeRequirement(@NotNull final String... args){
        this.enchantment = Enchantment.getByName(args[0]);
    }

    @Override
    public boolean control(@NotNull EnchantItemEvent controlled) {
        return controlled.getEnchantsToAdd().containsKey(this.enchantment);
    }
}
