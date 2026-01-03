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

@Parse(name = "@caster_eye_location", aliases = "@self_eye_location", description = "Targets the executor's eye location")
public class CasterEyeLocationTargeter implements Targeter<Location> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Location> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        if (!(context.getCaster() instanceof LivingEntity entity))
            return Lists.newArrayList();

        return Lists.newArrayList(entity.getEyeLocation());
    }
}
