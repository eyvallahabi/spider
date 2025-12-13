package io.github.shiryu.spider.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@RequirementInfo(name = "item_enchantment_level", controls = ItemStack.class)
public class ItemEnchantmentLevelRequirement implements Requirement<ItemStack> {

    private final Enchantment enchantment;
    private final int level;

    public ItemEnchantmentLevelRequirement(@NotNull final String... args){
        this.enchantment = Enchantment.getByName(args[0]);
        this.level = Integer.parseInt(args[1]);
    }

    @Override
    public boolean control(@NotNull ItemStack controlled) {
        return controlled.getEnchantmentLevel(this.enchantment) >= this.level;
    }
}
