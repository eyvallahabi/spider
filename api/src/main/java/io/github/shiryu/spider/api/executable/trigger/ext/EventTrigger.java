package io.github.shiryu.spider.api.executable.trigger.ext;

import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface EventTrigger extends Trigger, Listener {

    @Override
    default void register(@NotNull final Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
