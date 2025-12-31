package io.github.shiryu.spider.api.executable.requirement;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import org.jetbrains.annotations.NotNull;

public interface Requirement {

    boolean control(@NotNull final ExecutionContext context);

}
