package io.github.shiryu.spider.api.executable.targeter;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Targeter<T> {

    void initialize(@NotNull final ParseContext context);

    @Nullable
    T find(@NotNull final Trigger trigger, @NotNull final ExecutionContext context);

}
