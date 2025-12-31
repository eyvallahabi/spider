package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.StructureSearchResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@nearest_structure", aliases = "@ns", description = "Targets the nearest structure location")
public class NearestStructureTargeter implements Targeter<Location> {

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location center = context.caster().getLocation();

        final Structure structure = RegistryAccess
                .registryAccess()
                .getRegistry(RegistryKey.STRUCTURE)
                .getOrThrow(NamespacedKey.minecraft(context.getOrSet("structure", "stronghold")));

        final int radius = context.getOrSet("radius", 1000);
        final boolean findUnexplored = context.getOrSet("find_unexplored", false);

        final StructureSearchResult result = center.getWorld()
                .locateNearestStructure(
                        center,
                        structure,
                        radius,
                        findUnexplored
                );

        return result != null ? result.getLocation() : null;
    }
}
