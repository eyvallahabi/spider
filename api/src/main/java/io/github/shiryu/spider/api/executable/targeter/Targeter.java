package io.github.shiryu.spider.api.executable.targeter;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Targeter<T> {

    void initialize(@NotNull final ParseContext context);

    @NotNull
    List<T> find(@NotNull final Trigger trigger, @NotNull final ExecutionContext context);

}
