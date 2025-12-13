package io.github.shiryu.spider.sound.impl;

import io.github.shiryu.spider.sound.PlayableSound;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CompoundSound implements PlayableSound {

    private final List<PlayableSound> sounds = new ArrayList<>();

    public CompoundSound add(@NotNull final Sound sound){
        this.sounds.add(new SingularSound(sound, 1, 1));

        return this;
    }

    public CompoundSound add(@NotNull final Sound sound, final float pitch){
        this.sounds.add(new SingularSound(sound, 1, pitch));

        return this;
    }

    public CompoundSound add(@NotNull final Sound sound, final float pitch, final int... repeat){
        this.sounds.add(new RepeatableSound(sound, pitch, repeat));

        return this;
    }

    @Override
    public void play(@NotNull final Location location) {
        this.sounds.forEach(sound -> sound.play(location));
    }

    @Override
    public void play(@NotNull final Entity entity) {
        this.sounds.forEach(sound -> sound.play(entity));
    }
}
