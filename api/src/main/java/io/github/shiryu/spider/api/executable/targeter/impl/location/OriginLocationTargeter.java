package io.github.shiryu.spider.api.executable.targeter.impl.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@origin", description = "Targets the origin location")
public class OriginLocationTargeter implements Targeter<Location> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return List.of(context.get("location"));
    }
}
