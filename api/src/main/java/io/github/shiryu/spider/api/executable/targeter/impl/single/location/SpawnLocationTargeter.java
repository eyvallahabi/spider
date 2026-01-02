package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@spawnlocation", aliases = "@sl", description = "Targets the spawn location of the world")
public class SpawnLocationTargeter implements Targeter<Location> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final World world = context.get("world");

        if (world == null)
            return null;

        return world.getSpawnLocation();
    }
}
