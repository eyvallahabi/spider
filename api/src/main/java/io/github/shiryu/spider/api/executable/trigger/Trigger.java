package io.github.shiryu.spider.api.executable.trigger;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.registry.Registries;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Trigger {

    default void call(@NotNull final ExecutionContext context){
        Registries.EXECUTABLE.getExecutables().forEach(executable -> executable.accept(this, context));
    }

    void initialize(@NotNull final ParseContext context);

    void register(@NotNull final Plugin plugin);
}
