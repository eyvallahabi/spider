package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@location", aliases = {"@l", "@loc"}, description = "Targets a known location from the context")
public class KnownLocationTargeter implements Targeter<Location> {

    private double x,y,z;
    private float yaw, pitch;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.x = Double.parseDouble(context.targeter().getOrDefault("x", "0.0"));
        this.y = Double.parseDouble(context.targeter().getOrDefault("y", "0.0"));
        this.z = Double.parseDouble(context.targeter().getOrDefault("z", "0.0"));
        this.yaw = Float.parseFloat(context.targeter().getOrDefault("yaw", "0.0"));
        this.pitch = Float.parseFloat(context.targeter().getOrDefault("pitch", "0.0"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return Lists.newArrayList(
                new Location(
                        context.get("world"),
                        this.x,
                        this.y,
                        this.z,
                        this.yaw,
                        this.pitch
                )
        );
    }
}
