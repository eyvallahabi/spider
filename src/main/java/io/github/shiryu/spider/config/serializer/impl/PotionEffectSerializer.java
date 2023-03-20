package io.github.shiryu.spider.config.serializer.impl;

import io.github.shiryu.spider.config.BasicConfiguration;
import io.github.shiryu.spider.config.serializer.ConfigSerializer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class PotionEffectSerializer implements ConfigSerializer<PotionEffect> {

    @Override
    public @NotNull PotionEffect read(@NotNull final BasicConfiguration file, @NotNull final String path) {
        final PotionEffectType type = PotionEffectType.getByName(file.getOrSet(path + ".type", "ABSORPTION"));
        final int amplifier = file.getOrSet(path + ".amplifier", 0);
        final int duration = file.getOrSet(path + ".duration", 10);

        return type.createEffect(duration, amplifier);
    }

    @Override
    public void write(@NotNull final BasicConfiguration file, @NotNull final PotionEffect object, @NotNull final String path) {
        file.set(path + ".type", object.getType().getName());
        file.set(path + ".amplifier", object.getAmplifier());
        file.set(path + ".duration", object.getDuration());
    }
}
