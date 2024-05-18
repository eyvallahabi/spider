package io.github.shiryu.spider.bukkit.event.model;

import io.github.shiryu.spider.bukkit.SpiderPlugin;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface BukkitEventCaller {

    @NotNull
    List<BukkitEventListener> getListeners();

    default void addListener(@NotNull final BukkitEventListener listener){
        this.getListeners().add(listener);
    }

    default void register(){
        this.getListeners().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, SpiderPlugin.getPlugin()));
    }

}
