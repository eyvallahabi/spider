package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.ext.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@block_vein", description = "Targets all connected blocks of the same type")
public class BlockVeinTargeter implements MultiTargeter<Location> {

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Material material = Material.valueOf(context.getOrSet("material", "IRON_ORE"));
        final Location origin = context.get("block");

        if (origin == null || origin.getBlock().getType() != material)
            return null;

        return findVein(origin, material);
    }

    @NotNull
    private List<Location> findVein(@NotNull final Location origin, @NotNull final Material material){
        final List<Location> vein = Lists.newArrayList();
        final List<Location> toCheck = Lists.newArrayList(origin);
        final List<Location> checked = Lists.newArrayList();

        while (!toCheck.isEmpty()){
            final Location current = toCheck.removeFirst();
            checked.add(current);

            if (current.getBlock().getType() != material)
                continue;

            vein.add(current);

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (Math.abs(x) + Math.abs(y) + Math.abs(z) != 1)
                            continue;

                        final Location neighbor = current.clone().add(x, y, z);

                        if (!checked.contains(neighbor) && !toCheck.contains(neighbor)){
                            toCheck.add(neighbor);
                        }
                    }
                }
            }
        }

        return vein;
    }
}
