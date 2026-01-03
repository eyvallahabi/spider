package io.github.shiryu.spider.api.executable.targeter.impl.entity;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Parse(name = "@owner", description = "Targets the owner of the caster if the caster is a pet")
public class OwnerTargeter implements Targeter<Player> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull List<Player> find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.getCaster();

        if (!(caster instanceof Tameable tameable))
            return Lists.newArrayList();

        final AnimalTamer tamer = tameable.getOwner();

        if (tamer == null)
            return Lists.newArrayList();

        if (!(tamer instanceof Player player))
            return Lists.newArrayList();

        return List.of(player);
    }
}
