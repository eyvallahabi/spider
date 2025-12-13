package io.github.shiryu.spider.integration.world;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.shiryu.spider.api.integration.Integration;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.world.requirement.RegionRequirement;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WorldGuardIntegration implements Integration {

    public boolean isInRegions(@NotNull final Player player, final String... regions){
        return this.isInRegions(player.getLocation(), regions);
    }

    public boolean isInRegions(@NotNull final Location location, final String... regions){
        final RegionManager manager = WorldGuard.getInstance().getPlatform()
                .getRegionContainer()
                .get(BukkitAdapter.adapt(location.getWorld()));

        if (manager == null)
            return false;

        final ApplicableRegionSet set = manager.getApplicableRegions(BukkitAdapter.adapt(location).toVector().toBlockPoint());

        if (set.size() < 1)
            return false;

        for (final ProtectedRegion region : set){
            for (final String name : regions){
                if (region.getId().equalsIgnoreCase(name))
                    return true;
            }
        }

        return false;
    }

    public boolean hasFlagDisabled(@NotNull final Location location, @NotNull final Flag<?> flag){
        final RegionManager manager = WorldGuard.getInstance().getPlatform()
                .getRegionContainer()
                .get(BukkitAdapter.adapt(location.getWorld()));

        if (manager == null)
            return true;

        final ApplicableRegionSet set = manager.getApplicableRegions(BukkitAdapter.adapt(location).toVector().toBlockPoint());

        if (set.size() < 1)
            return true;

        for (final ProtectedRegion region : set){
            if (region.getFlags().containsKey(flag) && region.getFlag(flag) == StateFlag.State.DENY) {
                return true;
            }
        }

        return false;
    }


    @NotNull
    public String getRegion(@NotNull final Player player){
        return this.getRegion(player.getLocation());
    }

    @NotNull
    public String getRegion(@NotNull final Location location){
        final RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Objects.requireNonNull(location.getWorld())));

        assert manager != null;
        final ApplicableRegionSet set = manager.getApplicableRegions(BukkitAdapter.adapt(location).toVector().toBlockPoint());

        if (set.size() < 1)
            return "";

        String name = "";
        int priority = -1;

        for (final ProtectedRegion region : set){
            final int x = region.getPriority();

            if (name.isEmpty() || name.isBlank()){
                name = region.getId();
                priority = x;
            }

            if (x > priority){
                name = region.getId();
                priority = x;
            }
        }

        if (name.equals("global"))
            return "";

        return name;
    }


    @Override
    public void enable() {
        Registries.REQUIREMENT.register(RegionRequirement.class);
    }

    @Override
    public @NotNull String getPlugin() {
        return "WorldGuard";
    }

}
