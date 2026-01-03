package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.StructureSearchResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@nearest_structure", aliases = "@ns", description = "Targets the nearest structure location")
public class NearestStructureTargeter implements Targeter<Location> {

    private String structure;
    private int radius;
    private boolean findUnexplored;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.structure = context.targeter().getOrDefault("structure", "stronghold");
        this.radius = Integer.parseInt(context.targeter().getOrDefault("radius", "1000"));
        this.findUnexplored = Boolean.parseBoolean(context.targeter().getOrDefault("find_unexplored", "false"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location center = context.getCaster().getLocation();

        final Structure structure = RegistryAccess
                .registryAccess()
                .getRegistry(RegistryKey.STRUCTURE)
                .getOrThrow(NamespacedKey.minecraft(this.structure));

        final StructureSearchResult result = center.getWorld()
                .locateNearestStructure(
                        center,
                        structure,
                        this.radius,
                        this.findUnexplored
                );

        return result != null ? Lists.newArrayList(result.getLocation()) : Lists.newArrayList();
    }
}
