package io.github.shiryu.spider.bukkit.event.impl.armor;

import io.github.shiryu.spider.bukkit.SpiderPlugin;
import io.github.shiryu.spider.bukkit.event.impl.armor.impl.ArmorEquipEvent;
import io.github.shiryu.spider.bukkit.event.impl.armor.impl.ArmorUnequipEvent;
import io.github.shiryu.spider.bukkit.event.model.BukkitEventCaller;
import io.github.shiryu.spider.bukkit.event.model.BukkitEventListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

@Getter
public class ArmorEventCaller implements BukkitEventCaller {

    private final boolean equip;
    private final List<BukkitEventListener> listeners = new ArrayList<>();

    private final ConcurrentMap<UUID, ItemStack[]> contents = new ConcurrentHashMap<>();

    public ArmorEventCaller(final boolean equip) {
        this.equip = equip;

        Bukkit.getOnlinePlayers().forEach(player -> getContents().putIfAbsent(player.getUniqueId(), player.getEquipment().getArmorContents()));

        this.addListener((BukkitEventListener<InventoryClickEvent>) event -> {
            if (!(event.getWhoClicked() instanceof Player))
                return;

            final Inventory inventory = event.getClickedInventory();

            if (inventory == null)
                return;

            if (!(inventory.getType() == InventoryType.CRAFTING || inventory.getType() == InventoryType.PLAYER))
                return;

            if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.isShiftClick())
                this.call((Player)event.getWhoClicked());
        });

        this.addListener((BukkitEventListener<PlayerInteractEvent>) event ->{
            final ItemStack item = event.getItem();
            final Action action = event.getAction();

            if (item == null)
                return;

            if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK))
                return;

            if (!EnchantmentTarget.ARMOR.includes(item))
                return;

            this.call(event.getPlayer());
        });

        this.addListener((BukkitEventListener<PlayerDeathEvent>) event -> this.call(event.getEntity()));
        this.addListener((BukkitEventListener<PlayerJoinEvent>) event -> this.call(event.getPlayer()));
        this.addListener((BukkitEventListener<PlayerQuitEvent>) event -> this.call(event.getPlayer()));
        this.addListener((BukkitEventListener<PlayerItemBreakEvent>) event -> this.call(event.getPlayer()));

        this.addListener((BukkitEventListener<BlockDispenseEvent>) event->{
            final Location location = event.getBlock().getLocation();

            location.getWorld()
                    .getNearbyEntities(location, 6, 6, 6)
                    .stream()
                    .filter(entity -> entity instanceof Player)
                    .map(entity -> (Player) entity)
                    .forEach(this::call);
        });

        this.register();
    }

    private void call(final Player player) {
        new BukkitRunnable(){
            @Override
            public void run() {
                final ItemStack[] now = player.getEquipment().getArmorContents();
                final ItemStack[] saved = getContents().get(player.getUniqueId());

                IntStream.range(0, now.length)
                        .forEach(i ->{
                            if (saved != null && (now[i] != null && saved[i] != null && !now[i].toString().equalsIgnoreCase(saved[i].toString()))) {
                                if (equip){
                                    Bukkit.getPluginManager().callEvent(new ArmorUnequipEvent(player, saved[i]));
                                    Bukkit.getPluginManager().callEvent(new ArmorEquipEvent(player, now[i]));
                                }else{

                                }
                            }

                            if (equip) {
                                if (now[i] != null && (saved == null || saved[i] == null)) {
                                    Bukkit.getPluginManager().callEvent(new ArmorEquipEvent(player, now[i]));
                                } else {
                                    if (now[i] == null && (saved != null && saved[i] != null)) {
                                        Bukkit.getPluginManager().callEvent(new ArmorUnequipEvent(player, saved[i]));
                                    }
                                }
                            }
                        });
            }
        }.runTaskLater(SpiderPlugin.getPlugin(), 1L);

    }

}
