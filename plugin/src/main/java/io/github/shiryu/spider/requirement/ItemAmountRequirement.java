package io.github.shiryu.spider.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@RequirementInfo(name = "item_amount", controls = ItemStack.class)
public class ItemAmountRequirement implements Requirement<ItemStack> {

    private final int amount;

    public ItemAmountRequirement(@NotNull final String... args){
        this.amount = Integer.parseInt(args[0]);
    }

    @Override
    public boolean control(@NotNull ItemStack controlled) {
        return controlled.getAmount() >= amount;
    }
}
