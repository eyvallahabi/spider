package io.github.shiryu.spider.api.executable.targeter.impl.multi.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.MultiTargeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Parse(name = "@items_in_radius", aliases = "@iir", description = "Targets all items in a specified radius")
public class ItemsInRadiusTargeter implements MultiTargeter<Item> {

    private double radius;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.radius = Double.parseDouble(context.targeter().getOrDefault("radius", "5.0"));
    }

    @Override
    public @Nullable List<Item> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final var caster = context.caster();

        return caster.getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .toList();
    }
}
