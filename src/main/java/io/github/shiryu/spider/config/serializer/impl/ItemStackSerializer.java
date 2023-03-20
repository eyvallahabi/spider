package io.github.shiryu.spider.config.serializer.impl;

import com.cryptomorin.xseries.XMaterial;
import io.github.shiryu.spider.config.BasicConfiguration;
import io.github.shiryu.spider.config.serializer.ConfigSerializer;
import io.github.shiryu.spider.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ItemStackSerializer implements ConfigSerializer<ItemStack> {

    @Override
    public void write(@NotNull BasicConfiguration configuration, @NotNull final ItemStack item, @NotNull String path) {
        final XMaterial material = XMaterial.matchXMaterial(item.getType());

        configuration.set(path + ".material", material.name());
        configuration.set(path  + ".amount", item.getAmount());

        if (item.getItemMeta() != null){
            if (item.getItemMeta().hasDisplayName())
                configuration.set(path + ".name", item.getItemMeta().getDisplayName());

            if (item.getItemMeta().hasLore())
                configuration.set(path + ".lore", item.getItemMeta().getLore());
        }

        if (material.name().startsWith("LEATHER_")){
            final LeatherArmorMeta armorMeta = (LeatherArmorMeta) item.getItemMeta();

            if (armorMeta.getColor() != null){
                final Color color = armorMeta.getColor();

                configuration.set(path + ".color.red", color.getRed());
                configuration.set(path + ".color.green", color.getGreen());
                configuration.set(path + ".color.blue", color.getBlue());
            }
        }

        if (material == XMaterial.POTION){
            final Potion potion = Potion.fromItemStack(item);

            configuration.set(path + ".potionType", potion.getType().name());
            configuration.set(path + ".potionSplash", potion.isSplash());
            configuration.set(path + ".potionLevel", potion.getLevel());
        }

        if (!item.getEnchantments().isEmpty()){
            final AtomicInteger id = new AtomicInteger(0);

            item.getEnchantments().forEach((enchantment, level) ->{

                configuration.set(path + ".enchantments." + id + ".enchantment", enchantment.getName());
                configuration.set(path + ".enchantments." + id + ".level", level);

                id.incrementAndGet();
            });
        }
    }

    @Override
    public @NotNull ItemStack read(@NotNull BasicConfiguration configuration, @NotNull final String path) {
        final XMaterial material = XMaterial.matchXMaterial(configuration.getOrSet(path + ".material", "PAPER")).get();
        final String name = configuration.getOrSet(path + ".name", "");
        final int amount = configuration.getOrSet(path + ".amount", 1);

        AtomicReference<ItemStack> item = new AtomicReference<>();

        item.set(
                ItemBuilder.from(material)
                        .amount(amount)
                        .build()
        );

        if (!name.isEmpty()){
            item.set(
                    ItemBuilder.from(item.get())
                            .name(name)
                            .build()
            );
        }

        final List<String> lore = configuration.getOrSet(path + ".lore", new ArrayList<>());

        if (lore.isEmpty()){
            item.set(ItemBuilder.from(item.get())
                    .name(name)
                    .build());
        }else{
            item.set(ItemBuilder.from(item.get())
                    .name(name)
                    .lore(lore)
                    .build());
        }

        if (material.name().startsWith("LEATHER_")){
            final Color color = Color.fromRGB(
                    configuration.getOrSet(path + ".color.red", 0),
                    configuration.getOrSet(path + ".color.green", 0),
                    configuration.getOrSet(path + ".color.blue", 0)
            );

            if (color != null)
                item.set(
                        ItemBuilder.from(item.get())
                                .dye(color)
                                .build()
                );
        }

        if (material == XMaterial.POTION){
            item.set(
                    ItemBuilder.from(item.get())
                            .potion(PotionType.valueOf(configuration.getOrSet(path + ".potionType", "INSTANT_HEALTH")),
                                    configuration.getOrSet(path + ".potionSplash", false),
                                    configuration.getOrSet(path + ".potionLevel", 1))

                            .build()
            );
        }


        configuration.getSection(path + ".enchantments")
                .ifPresent(section ->
                        section.forEach(key ->{
                            final Enchantment enchantment = Enchantment.getByName(section.getOrSet(key + ".enchantment", ""));
                            final int level = section.getOrSet(key + ".level", 0);

                            item.set(
                                    ItemBuilder.from(item.get())
                                            .enchant(enchantment, level)
                                            .build()
                            );
                        })
                );

        return item.get();
    }

}
