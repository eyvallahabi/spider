package io.github.shiryu.spider.bukkit.config.item;

import com.cryptomorin.xseries.XMaterial;
import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import io.github.shiryu.spider.bukkit.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class ItemStackConfigItem implements ConfigItem<ItemStack> {

    private ItemStack value;

    @Override
    public void save(@NotNull Config config, @NotNull String path) {
        XMaterial material = XMaterial.matchXMaterial(value.getType());

        if (material == null) material = XMaterial.PAPER;

        config.set(path + ".material", material.name());
        config.set(path + ".amount", value.getAmount());

        if (this.value.getItemMeta() != null){
            if (this.value.getItemMeta().hasDisplayName())
                config.set(path + ".name", this.value.getItemMeta().getDisplayName());
            if (this.value.getItemMeta().hasLore())
                config.set(path + ".lore", this.value.getItemMeta().getLore());
        }

        if (material.name().startsWith("LEATHER_")){
            LeatherArmorMeta armorMeta = (LeatherArmorMeta)this.value.getItemMeta();
            if (armorMeta.getColor() != null) {
                final Color color = armorMeta.getColor();

                config.set(path + ".color.red", Integer.valueOf(color.getRed()));
                config.set(path + ".color.green", Integer.valueOf(color.getGreen()));
                config.set(path + ".color.blue", Integer.valueOf(color.getBlue()));
            }
        }

        if (material == XMaterial.POTION) {
            final Potion potion = Potion.fromItemStack(this.value);

            config.set(path + ".potionType", potion.getType().name());
            config.set(path + ".potionSplash", Boolean.valueOf(potion.isSplash()));
            config.set(path + ".potionLevel", Integer.valueOf(potion.getLevel()));
        }

        if (!this.value.getEnchantments().isEmpty()) {
            final AtomicInteger id = new AtomicInteger(0);

            this.value.getEnchantments().forEach((enchantment, level) -> {
                config.set(path + ".enchantments." + path + ".enchantment", enchantment.getName());
                config.set(path + ".enchantments." + path + ".level", level);

                id.incrementAndGet();
            });
        }
    }

    @Override
    public void get(@NotNull Config config, @NotNull String path) {
        final XMaterial material = XMaterial.matchXMaterial(config.getOrSet(path + ".material", "PAPER")).get();
        final String name = config.getOrSet(path + ".name", "");
        final int amount = config.getOrSet(path + ".amount", 1);

        final AtomicReference<ItemStack> item = new AtomicReference<>();

        item.set(
                ItemBuilder.from(material)
                        .amount(amount)
                        .build());

        if (!name.isEmpty())
            item.set(
                    ItemBuilder.from(item.get())
                            .name(name)
                            .build());

        final List<String> lore = config.getOrSet(path + ".lore", new ArrayList());

        if (lore.isEmpty()) {
            item.set(ItemBuilder.from(item.get())
                    .name(name)
                    .build());
        } else {
            item.set(ItemBuilder.from(item.get())
                    .name(name)
                    .lore(lore)
                    .build());
        }

        if (material.name().startsWith("LEATHER_")) {
            Color color = Color.fromRGB((
                    config.getOrSet(path + ".color.red", 0)),
                    config.getOrSet(path + ".color.green", 0),
                    config .getOrSet(path + ".color.blue", 0));

            if (color != null)
                item.set(
                        ItemBuilder.from(item.get())
                                .dye(color)
                                .build());

        }

        if (material == XMaterial.POTION)
            item.set(
                    ItemBuilder.from(item.get())
                            .potion(
                                    PotionType.valueOf(
                                            config.getOrSet(path + ".potionType", "INSTANT_HEALTH")),
                                            config.getOrSet(path + ".potionSplash", false),
                                            config.getOrSet(path + ".potionLevel", 1))
                            .build());


        config.getSection(path + ".enchantments").forEach(key ->{
            final Enchantment enchantment = Enchantment.getByName(config.getOrSet(path + ".enchantments." + key + ".enchantment", "PROTECTION_ENVIRONMENTAL"));
            final int level = config.getOrSet(path + ".enchantments." + key + ".level", 0);

            item.set(
                    ItemBuilder.from(item.get())
                            .enchant(enchantment, level)
                            .build());
        });

        this.value = item.get();
    }
}
