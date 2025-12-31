package io.github.shiryu.spider.api.executable.trigger.impl;

import io.github.shiryu.spider.api.executable.trigger.Trigger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class TriggerPack implements Trigger {

    private final Set<Trigger> triggers;

    public TriggerPack(@NotNull final Trigger... triggers){
        this.triggers = Set.of(triggers);
    }

    @Override
    public void register(@NotNull Plugin plugin) {
        triggers.forEach(trigger -> trigger.register(plugin));
    }
}
