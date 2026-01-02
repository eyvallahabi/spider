package io.github.shiryu.spider.api.executable.action.impl;

import io.github.shiryu.spider.api.executable.action.Action;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Parse(name = "sound", description = "Plays a sound at the target location or entity.")
public class SoundAction implements Action {

    private Sound sound;
    private float volume;
    private float pitch;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.sound = Registry.SOUNDS.get(NamespacedKey.minecraft(context.action().getOrDefault("name", "entity.experience_orb.pickup")));
        this.volume = Float.parseFloat(context.action().getOrDefault("volume", "1"));
        this.pitch = Float.parseFloat(context.action().getOrDefault("pitch", "1"));
    }

    @Override
    public void execute(@NotNull ExecutionContext context) {
        final Object target = context.get("target");

        if (target instanceof Entity entity){
            entity.getWorld().playSound(
                    entity.getLocation(),
                    this.sound,
                    this.volume,
                    this.pitch
            );
        }else if (target instanceof Location location){
            location.getWorld().playSound(
                    location,
                    this.sound,
                    this.volume,
                    this.pitch
            );
        }
    }
}
