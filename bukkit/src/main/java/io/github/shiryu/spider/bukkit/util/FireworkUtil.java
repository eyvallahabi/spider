package io.github.shiryu.spider.bukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@UtilityClass
public class FireworkUtil {

    public void spawnFirework(@NotNull final Location location, final int colorSize, final int fadeSize){
        final Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK_ROCKET);
        final FireworkMeta meta = firework.getFireworkMeta();

        final FireworkEffect effect = FireworkEffect.builder()
                .flicker(new Random().nextBoolean())
                .trail(new Random().nextBoolean())
                .withColor(getRandomColor(colorSize))
                .withFade(getRandomColor(fadeSize))
                .with(getRandomType())
                .build();

        meta.addEffect(effect);
        meta.setPower(new Random().nextInt(2) + 1);

    }

    public void spawnFirework(@NotNull final Location location){
        spawnFirework(location, 1, 1);
    }

    private FireworkEffect.Type getRandomType(){
        final int random = new Random().nextInt(6);

        return Arrays.stream(FireworkEffect.Type.values())
                .filter(type -> type.ordinal() == random)
                .findFirst()
                .orElse(FireworkEffect.Type.BALL);
    }

    private Color[] getRandomColor(final int size){
        final Color[] colors = new Color[size];

        IntStream.rangeClosed(0, size)
                .forEach(i -> colors[i] = getRandomColor());

        return colors;
    }

    private Color getRandomColor(){
        final int random = new Random().nextInt(17);

        final Color[] colors = new Color[]{
                Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY,
                Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE,
                Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL,
                Color.WHITE, Color.YELLOW
        };

        final AtomicReference<Color> selected = new AtomicReference<>(Color.AQUA);

        IntStream.rangeClosed(0, 17)
                .forEach(i ->{
                    if (i == random)
                        selected.set(colors[i]);
                });

        return selected.get();
    }
}
