package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@caster_location", aliases = "@self_location", description = "Targets the executor's location")
public class CasterLocationTargeter implements Targeter<Location> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.caster().getLocation();
    }
}
