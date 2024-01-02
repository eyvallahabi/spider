package io.github.shiryu.spider.bukkit.config.item;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

@Getter
public class PotionEffectConfigItem implements ConfigItem<PotionEffect> {

    private PotionEffect value;

    @Override
    public void save(@NotNull Config config, @NotNull String path) {
        config.set(path + ".type", this.value.getType().getName());
        config.set(path + ".amplifier", this.value.getAmplifier());
        config.set(path + ".duration", this.value.getDuration());
    }

    @Override
    public void get(@NotNull Config config, @NotNull String path) {
        this.value = PotionEffectType.getByName(config.get(path + ".type"))
                .createEffect(
                        config.get(path + ".duration"),
                        config.get(path + ".amplifier")
                );
    }
}
