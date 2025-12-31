package io.github.shiryu.spider.api.executable.targeter.impl.single.location;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@forward", description = "Targets the forward location of the caster")
public class ForwardLocationTargeter implements Targeter<Location> {

    @Override
    public @Nullable Location find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final int distance = context.getOrSet("forward_distance", 5);
        final int rotate = context.getOrSet("forward_rotate", 0);

        final boolean useEyeLocation = context.getOrSet("forward_use_eye_location", false);
        final boolean lockPitch = context.getOrSet("forward_lock_pitch", false);

        final Location origin = useEyeLocation ? ((LivingEntity)context.caster()).getEyeLocation() : context.caster().getLocation();

        final Location target = origin.clone();

        if (lockPitch)
            target.setPitch(0);

        target.setYaw(target.getYaw() + rotate);
        target.add(target.getDirection().multiply(distance));
        return target;
    }
}
