package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@items_in_radius", aliases = "@iir", description = "Targets all items in a specified radius")
public class ItemsInRadiusTargeter implements Targeter<Item> {

    private double radius;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
    }

    @Override
    public @NonNull List<Item> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final var caster = context.getCaster();

        return caster.getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .toList();
    }
}
