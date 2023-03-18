package io.github.shiryu.spider.config.serializer.impl;

import io.github.shiryu.spider.config.BasicConfiguration;
import io.github.shiryu.spider.config.serializer.ConfigSerializer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackSerializer implements ConfigSerializer<ItemStack> {

    @Override
    public void write(@NotNull BasicConfiguration configuration, @NotNull final ItemStack item, @NotNull String path) {

    }

    @Override
    public @NotNull ItemStack read(@NotNull BasicConfiguration from, @NotNull final String path) {
        return null;
    }

}
