package io.github.shiryu.spider.api.executable.targeter.impl.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@sphere", description = "Targets locations in a sphere")
public class SphereTargeter implements Targeter<Location> {

    private double radius;
    private double step;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
        this.step = Double.parseDouble(context.targeter().getOrDefault("step", "1.0"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return SpiderLocation.from(context.getCaster().getLocation()).sphere(
                radius,
                step
        ).stream().map(SpiderLocation::convert).toList();
    }
}
