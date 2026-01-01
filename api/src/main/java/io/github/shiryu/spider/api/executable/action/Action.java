package io.github.shiryu.spider.api.executable.action;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import org.jetbrains.annotations.NotNull;

public interface Action {

    void execute(@NotNull final ExecutionContext context);

}
