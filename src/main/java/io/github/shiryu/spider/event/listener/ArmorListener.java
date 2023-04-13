package io.github.shiryu.spider.event.listener;

import io.github.shiryu.spider.SpiderPlugin;
import io.github.shiryu.spider.event.player.impl.armor.PlayerArmorEquipEvent;
import io.github.shiryu.spider.event.player.impl.armor.PlayerArmorUnequipEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ArmorListener implements Listener {

    private final ConcurrentMap<UUID, ItemStack[]> contents = new ConcurrentHashMap<>();

    public ArmorListener() {
        Bukkit.getOnlinePlayers().forEach(player -> getContents().putIfAbsent(player.getUniqueId(), player.getEquipment().getArmorContents()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        final Inventory inventory = event.getClickedInventory();
        if (inventory != null && (inventory.getType() == InventoryType.CRAFTING || inventory.getType() == InventoryType.PLAYER)) {
            if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.isShiftClick()) {
                check((Player) event.getWhoClicked());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final PlayerInteractEvent event) {
        final Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack item = event.getItem();
            if (item == null) {
                return;
            }
            if (EnchantmentTarget.ARMOR.includes(item)) check(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final PlayerDeathEvent event) {
        check(event.getEntity());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final PlayerJoinEvent event) {
        check(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final PlayerQuitEvent event) {
        getContents().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final BlockDispenseEvent event) {
        final ItemStack item = event.getItem();
        final Location location = event.getBlock().getLocation();
        location.getWorld().getNearbyEntities(location, 6, 6, 6).stream().filter(e -> e instanceof Player).map(e -> (Player) e).forEach(this::check);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public final void onEvent(final PlayerItemBreakEvent event) {
        check(event.getPlayer());
    }

    private void check(final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack[] now = player.getEquipment().getArmorContents();
                ItemStack[] saved = getContents().get(player.getUniqueId());
                for (int i = 0; i < now.length; i++) {
                    if (now[i] == null && (saved != null && saved[i] != null)) {
                        Bukkit.getPluginManager().callEvent(new PlayerArmorUnequipEvent(player, saved[i]));
                    } else if (now[i] != null && (saved == null || saved[i] == null)) {
                        Bukkit.getPluginManager().callEvent(new PlayerArmorEquipEvent(player, now[i]));
                    } else if (saved != null && (now[i] != null && saved[i] != null && !now[i].toString().equalsIgnoreCase(saved[i].toString()))) {
                        Bukkit.getPluginManager().callEvent(new PlayerArmorUnequipEvent(player, saved[i]));
                        Bukkit.getPluginManager().callEvent(new PlayerArmorEquipEvent(player, now[i]));
                    }
                }
                getContents().put(player.getUniqueId(), now);
            }
        }.runTaskLater(SpiderPlugin.getPlugin(), 1L);
    }

    private ConcurrentMap<UUID, ItemStack[]> getContents() {
        return contents;
    }
}
