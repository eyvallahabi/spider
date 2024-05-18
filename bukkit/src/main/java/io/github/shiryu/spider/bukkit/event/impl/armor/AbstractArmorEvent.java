package io.github.shiryu.spider.bukkit.event.impl.armor;

import io.github.shiryu.spider.bukkit.event.AbstractSpiderEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public abstract class AbstractArmorEvent extends AbstractSpiderEvent {

    private final Player player;
    private final ItemStack item;

}
