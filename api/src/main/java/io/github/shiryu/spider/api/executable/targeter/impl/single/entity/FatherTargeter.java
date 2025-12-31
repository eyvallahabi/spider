package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@father", description = "Targets the father of the bred entity")
public class FatherTargeter implements Targeter<Entity> {

    @Override
    public @Nullable Entity find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.get("father");
    }
}
