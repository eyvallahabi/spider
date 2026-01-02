package io.github.shiryu.spider.api.executable.targeter.impl.single.entity;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@Parse(name = "@trigger", description = "Targets the trigger object")
public class TriggerTargeter implements Targeter<Object> {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @Override
    public @Nullable Object find(@NotNull Trigger trigger, @NotNull ExecutionContext context) {
        return context.get("trigger");
    }
}
