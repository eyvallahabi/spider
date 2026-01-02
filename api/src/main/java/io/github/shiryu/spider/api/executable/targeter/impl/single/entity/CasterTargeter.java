package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@Parse(name = "@caster", aliases = {"@self", "@s", "@me", "@c"}, description = "Targets the caster of the trigger")
public class CasterTargeter implements Targeter<Entity> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @NonNull Entity find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.caster();
    }
}
