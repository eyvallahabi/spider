package io.github.shiryu.spider.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@UtilityClass
public class Listeners {

    public void register(@NotNull final Plugin plugin, @NotNull final Listener... listeners){
        if (listeners == null || listeners.length == 0)
            return;

        Arrays.stream(listeners)
                .forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, plugin));
    }
}
