package io.github.shiryu.spider.api.executable.trigger;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Trigger {

    default void call(@NotNull final ExecutionContext context){

    }

    void register(@NotNull final Plugin plugin);
}
