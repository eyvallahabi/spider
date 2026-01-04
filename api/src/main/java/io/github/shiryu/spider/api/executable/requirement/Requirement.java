package io.github.shiryu.spider.api.executable.requirement;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import org.jetbrains.annotations.NotNull;

public interface Requirement {

    void initialize(@NotNull final ParseContext context);

    boolean control(@NotNull final ExecutionContext context);

}
