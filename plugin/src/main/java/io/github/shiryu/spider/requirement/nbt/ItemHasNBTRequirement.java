package io.github.shiryu.spider.requirement.nbt;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@RequirementInfo(name = "item_has_nbt", controls = ItemStack.class)
public class ItemHasNBTRequirement implements Requirement<ItemStack> {

    private final String key;

    public ItemHasNBTRequirement(@NotNull final String... args){
        this.key = args[0];
    }

    @Override
    public boolean control(@NotNull ItemStack controlled) {
        final ReadableNBT nbt = NBT.readNbt(controlled);

        return nbt.hasTag(this.key);
    }
}
