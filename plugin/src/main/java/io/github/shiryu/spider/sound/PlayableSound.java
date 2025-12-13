package io.github.shiryu.spider.sound;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface PlayableSound {

    default void play(@NotNull final Entity entity){
        this.play(entity.getLocation());
    }

    void play(@NotNull final Location location);
}
