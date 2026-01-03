package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@passenger", description = "Targets the passenger of the caster entity")
public class PassengerTargeter implements Targeter<Entity> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Entity> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.getCaster();

        final List<Entity> passengers = caster.getPassengers();

        if (passengers.isEmpty())
            return Lists.newArrayList();

        return passengers;
    }
}
