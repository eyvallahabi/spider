package io.github.shiryu.spider.api.factory.impl;

import io.github.shiryu.spider.api.factory.converter.ItemStackArrayConverter;
import io.github.shiryu.spider.api.factory.converter.ItemStackConverter;
import org.bukkit.inventory.ItemStack;

public class ItemStackFactory extends AbstractFactory {

    @Override
    public void register() {
        register(ItemStack.class, new ItemStackConverter());
        register(ItemStack[].class, new ItemStackArrayConverter());
    }

}
