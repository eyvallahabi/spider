package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@triggerlocation", aliases = "@trl", description = "Targets the location of the trigger")
public class TriggerLocationTargeter implements Targeter<Location> {

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Object object = context.get("trigger");

        if (object instanceof Location location)
            return location;

        if (object instanceof Entity entity)
            return entity.getLocation();

        if (object instanceof Block block)
            return block.getLocation();

        return null;
    }
}
