package io.github.shiryu.spider.bukkit.sound.impl;

import io.github.shiryu.spider.bukkit.sound.PlayableSound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class SingularSound implements PlayableSound {

    private final Sound sound;
    private final float volume;
    private final float pitch;

    public void play(@NotNull final Location location){
        location.getWorld().playSound(
                location,
                this.sound,
                SoundCategory.MASTER,
                this.volume,
                this.pitch
        );
    }
}
