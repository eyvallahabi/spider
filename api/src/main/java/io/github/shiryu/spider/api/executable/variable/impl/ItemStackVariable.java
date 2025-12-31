package io.github.shiryu.spider.api.executable.variable.impl;

import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemStackVariable implements Variable<ItemStack> {

    private final String name;
    private ItemStack value;

}
