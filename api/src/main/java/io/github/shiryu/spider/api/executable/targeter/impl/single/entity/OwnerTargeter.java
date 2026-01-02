package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@owner", description = "Targets the owner of the caster if the caster is a pet")
public class OwnerTargeter implements Targeter<Player> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable Player find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        final Entity caster = context.caster();

        if (!(caster instanceof Tameable tameable))
            return null;

        return (Player) tameable.getOwner();
    }
}
