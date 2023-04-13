package io.github.shiryu.spider.event.player.impl.armor;

import io.github.shiryu.spider.event.player.AbstractSpiderPlayerEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerArmorEquipEvent extends AbstractSpiderPlayerEvent {

    private final ItemStack armor;

    public PlayerArmorEquipEvent(@NotNull final Player player, @NotNull final ItemStack armor) {
        super(player);

        this.armor = armor;
    }
}
