package io.github.shiryu.spider.api.executable.requirement;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Requirement {

    void initialize(@NotNull final Map<String, String> args);

    boolean control(@NotNull final ExecutionContext context);

}
