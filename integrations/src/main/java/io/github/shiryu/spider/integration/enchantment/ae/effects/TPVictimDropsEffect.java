package io.github.shiryu.spider.integration.enchantment.ae.effects;

import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TPVictimDropsEffect extends AdvancedEffect {

    public TPVictimDropsEffect(@NotNull final JavaPlugin plugin){
        super(plugin, "TP_VICTIM_DROPS", "Teleports the drops of the victim to the attacker.", "TP Victim Drops", 5);
    }

    @Override
    public boolean executeEffect(ExecutionTask task, LivingEntity entity, String[] args) {
        if (!(task.getBuilder().getEvent() instanceof EntityDeathEvent death))
            return true;

        final Player killer = death.getEntity().getKiller();

        if (killer == null)
            return true;

        final List<ItemStack> drops = new ArrayList<>(death.getDrops());

        if (drops.isEmpty())
            return true;

        for (ItemStack item : drops) {
            Map<Integer, ItemStack> leftovers = killer.getInventory().addItem(item);
            if (!leftovers.isEmpty()) {
                leftovers.values().forEach(left ->
                        killer.getWorld().dropItemNaturally(killer.getLocation(), left)
                );
            }
        }

        death.getDrops().clear();

        killer.playSound(killer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.9f, 1.4f);

        return true;
    }

}
