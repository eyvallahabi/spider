package io.github.shiryu.spider.bukkit.cooldown;

import io.github.shiryu.spider.api.common.TimeUtil;
import io.github.shiryu.spider.api.common.cooldown.Cooldown;
import io.github.shiryu.spider.api.common.cooldown.entry.CompoundEntry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class BukkitCooldown implements Cooldown<Player> {

    private final Map<UUID, CompoundEntry> entries = new LinkedHashMap<>();

    @Override
    public void put(@NotNull Player player, @NotNull String id, long amount) {
        if (active(player, id))
            return;

        CompoundEntry entry = this.entries.get(player);

        if (entry == null)
            entry = new CompoundEntry();

        entry.add(id, player.getUniqueId(), amount);

        this.entries.put(player.getUniqueId(), entry);
    }

    @Override
    public boolean active(@NotNull Player player, @NotNull String id) {
        final CompoundEntry entry = this.entries.get(player.getUniqueId());

        if (entry == null){
            this.entries.put(player.getUniqueId(), new CompoundEntry());

            return false;
        }

        boolean result = entry.active(player.getUniqueId(), id);

        if (result)
            player.sendMessage(ChatColor.RED + "You must wait " + TimeUtil.readableTime(entry.getEntry(player.getUniqueId(), id).getFinish() - System.currentTimeMillis()) + " before using this ability again.");

        return result;
    }
}
