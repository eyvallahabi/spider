package io.github.shiryu.spider.bukkit.event.model;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public interface BukkitEventListener<E extends Event> extends Listener {

    void listen(@NotNull final E event);

}
