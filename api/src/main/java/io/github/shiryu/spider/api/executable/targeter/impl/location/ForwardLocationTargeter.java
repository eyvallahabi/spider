package io.github.shiryu.spider.api.executable.targeter.impl.location;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@forward", description = "Targets the forward location of the caster")
public class ForwardLocationTargeter implements Targeter<Location> {

    private int distance;
    private int rotate;

    private boolean useEyeLocation;
    private boolean lockPitch;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.distance = Integer.parseInt(context.targeter().getOrDefault("distance", "5"));
        this.rotate = Integer.parseInt(context.targeter().getOrDefault("rotate", "0"));
        this.useEyeLocation = Boolean.parseBoolean(context.targeter().getOrDefault("use_eye_location", "false"));
        this.lockPitch = Boolean.parseBoolean(context.targeter().getOrDefault("lock_pitch", "false"));
    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Location origin = this.useEyeLocation ? ((LivingEntity)context.getCaster()).getEyeLocation() : context.getCaster().getLocation();

        final Location target = origin.clone();

        if (this.lockPitch)
            target.setPitch(0);

        target.setYaw(target.getYaw() + this.rotate);
        target.add(target.getDirection().multiply(this.distance));

        return Lists.newArrayList(target);
    }
}
