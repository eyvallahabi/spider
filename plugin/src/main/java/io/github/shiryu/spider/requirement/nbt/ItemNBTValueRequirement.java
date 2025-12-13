package io.github.shiryu.spider.requirement.nbt;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@RequirementInfo(name = "item_nbt_value", controls = ItemStack.class)
public class ItemNBTValueRequirement<T> implements Requirement<ItemStack> {

    private final String key;
    private final Class<T> clazz;
    private final T value;

    public ItemNBTValueRequirement(@NotNull final String... args){
        this.key = args[0];
        final String type = args[1];
        final String value = args[2];

        switch (type.toLowerCase()){
            case "string" -> {
                this.clazz = (Class<T>) String.class;
                this.value = (T) value;
            }
            case "int" -> {
                this.clazz = (Class<T>) Integer.class;
                this.value = (T) Integer.valueOf(value);
            }
            case "double" -> {
                this.clazz = (Class<T>) Double.class;
                this.value = (T) Double.valueOf(value);
            }
            case "boolean" -> {
                this.clazz = (Class<T>) Boolean.class;
                this.value = (T) Boolean.valueOf(value);
            }
            default -> throw new IllegalArgumentException("Unknown NBT value type: " + type);
        }
    }

    @Override
    public boolean control(@NotNull ItemStack controlled) {
        final ReadableNBT nbt = NBT.readNbt(controlled);

        if (!nbt.hasTag(this.key))
            return false;

        final T get = nbt.getOrNull(this.key, clazz);

        if (get == null)
            return false;

        return get.equals(this.value);
    }
}
