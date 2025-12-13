package io.github.shiryu.spider.api.integration;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public interface Integration {

    @NotNull
    String getPlugin();

    void enable();

    default boolean isEnabled() {
        return Bukkit.getPluginManager().getPlugin(getPlugin()) != null;
    }

}
