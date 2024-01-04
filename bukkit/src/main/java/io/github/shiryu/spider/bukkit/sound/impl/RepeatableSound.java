package io.github.shiryu.spider.bukkit.sound.impl;

import io.github.shiryu.spider.bukkit.SpiderPlugin;
import io.github.shiryu.spider.bukkit.sound.PlayableSound;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class RepeatableSound implements PlayableSound {

    private final Sound sound;
    private final float pitch;
    private final int[] repeat;

    public RepeatableSound(@NotNull final Sound sound, final float pitch, final int... repeat) {
        this.sound = sound;
        this.pitch = pitch;
        this.repeat = repeat;
    }

    @Override
    public void play(@NotNull Location location) {
        final World world = location.getWorld();

        runRepeatableTaskLater(() -> world.playSound(location, sound, SoundCategory.MASTER, 4, pitch), repeat);
    }

    @Override
    public void play(@NotNull Entity entity) {
        final World world = entity.getLocation().getWorld();

        runRepeatableTaskLater(() -> world.playSound(entity.getLocation(), sound, SoundCategory.MASTER, 4, pitch), repeat);
    }

    public void runRepeatableTaskLater(Runnable runnable, @NotNull int... delays) {
        int delay = 0;

        for (int i : delays) {
            delay += i;
            Bukkit.getScheduler().runTaskLater(SpiderPlugin.getPlugin(), runnable, delay);
        }
    }

}
