package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import io.github.shiryu.spider.api.location.SpiderLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BlocksNearOriginTargeter implements Targeter<Location> {

    private String shape;
    private double radius, radiusY,noise;
    private boolean noAir, onlyAir;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.shape = context.targeter().getOrDefault("shape", "sphere").toLowerCase();
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
        this.radiusY = Double.parseDouble(context.targeter().getOrDefault("radius_y", String.valueOf(this.radius)));
        this.noise = Double.parseDouble(context.targeter().getOrDefault("noise", "0.0"));
        this.noAir = Boolean.parseBoolean(context.targeter().getOrDefault("no_air", "false"));
        this.onlyAir = Boolean.parseBoolean(context.targeter().getOrDefault("only_air", "false"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = context.get("location");

        if (origin == null)
            return Lists.newArrayList();

        return switch (shape) {
            case "sphere" -> SpiderLocation.from(origin).blocksInSphere(
                    radius,
                    radiusY,
                    noise,
                    noAir,
                    onlyAir
            ).stream().map(SpiderLocation::convert).toList();
            case "cube" -> SpiderLocation.from(origin).blocksInCube(
                    radius,
                    radiusY,
                    noise,
                    noAir,
                    onlyAir
            ).stream().map(SpiderLocation::convert).toList();
            default -> Lists.newArrayList();
        };
    }
}
