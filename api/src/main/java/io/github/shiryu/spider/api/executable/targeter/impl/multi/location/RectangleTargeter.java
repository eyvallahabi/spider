package io.github.shiryu.spider.api.executable.targeter.impl.multi.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@rectangle", description = "Targets locations in a rectangle")
public class RectangleTargeter implements MultiTargeter<Location> {

    private double length,width,step;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.length = Double.parseDouble(context.targeter().getOrDefault("length", "5.0"));
        this.width = Double.parseDouble(context.targeter().getOrDefault("width", "5.0"));
        this.step = Double.parseDouble(context.targeter().getOrDefault("step", "1.0"));
    }

    @Override
    public @Nullable List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.caster().getLocation();

        return SpiderLocation.from(origin).rectangle(length, width, step)
                .stream().map(SpiderLocation::convert).toList();
    }
}
