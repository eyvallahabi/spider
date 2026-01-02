package io.github.shiryu.spider.api.executable.action;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import org.jetbrains.annotations.NotNull;

public interface Action {

    void initialize(@NotNull final ParseContext context);

    void execute(@NotNull final ExecutionContext context);

}
