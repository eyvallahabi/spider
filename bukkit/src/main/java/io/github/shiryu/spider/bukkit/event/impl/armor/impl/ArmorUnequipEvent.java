package io.github.shiryu.spider.bukkit.event.impl.armor.impl;

import io.github.shiryu.spider.bukkit.event.impl.armor.AbstractArmorEvent;
import io.github.shiryu.spider.bukkit.event.impl.armor.ArmorEventCaller;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorUnequipEvent extends AbstractArmorEvent {

    public ArmorUnequipEvent(@NotNull final Player player, @NotNull final ItemStack item) {
        super(player, item);

        new ArmorEventCaller(false);
    }
}
