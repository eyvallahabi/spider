package io.github.shiryu.spider.configuration.value;

import com.cryptomorin.xseries.XMaterial;
import io.github.shiryu.spider.configuration.Config;
import io.github.shiryu.spider.configuration.ConfigValue;
import io.github.shiryu.spider.util.item.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@AllArgsConstructor
public class ItemValue implements ConfigValue<ItemStack> {

    private final String path;

    private ItemStack value;

    public ItemValue(@NotNull final String path){
        this(path, null);
    }

    @Override
    public ConfigValue<ItemStack> load(@NotNull Config config) {
        final XMaterial material = XMaterial.matchXMaterial(config.getOrSet(path + ".material", "PAPER")).get();
        final String name = config.getOrSet(path + ".name", "");
        final int amount = config.getOrSet(path + ".amount", 1);

        AtomicReference<ItemStack> item = new AtomicReference<>(
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

        final List<String> lore = config.getOrSet(path + ".lore", new ArrayList<>());

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
                    config.getOrSet(path + ".color.red", 0),
                    config.getOrSet(path + ".color.green", 0),
                    config.getOrSet(path + ".color.blue", 0)
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
                            .potion(PotionType.valueOf(config.getOrSet(path + ".potionType", "INSTANT_HEALTH")),
                                    config.getOrSet(path + ".potionSplash", false),
                                    config.getOrSet(path + ".potionLevel", 1))

                            .build()
            );
        }

        config.getSection(path + ".enchantments")
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

        this.value = item.get();

        return this;
    }

    @Override
    public ConfigValue<ItemStack>  save(@NotNull Config config) {
        final XMaterial material = XMaterial.matchXMaterial(this.value.getType());

        config.set(this.path + ".material", material.name());
        config.set(this.path  + ".amount", this.value.getAmount());

        if (this.value.getItemMeta() != null){
            if (this.value.getItemMeta().hasDisplayName())
                config.set(path + ".name", this.value.getItemMeta().getDisplayName());

            if (this.value.getItemMeta().hasLore())
                config.set(path + ".lore", this.value.getItemMeta().getLore());
        }

        if (material.name().startsWith("LEATHER_")){
            final LeatherArmorMeta armorMeta = (LeatherArmorMeta) this.value.getItemMeta();

            if (armorMeta.getColor() != null){
                final Color color = armorMeta.getColor();

                config.set(path + ".color.red", color.getRed());
                config.set(path + ".color.green", color.getGreen());
                config.set(path + ".color.blue", color.getBlue());
            }
        }

        if (material == XMaterial.POTION){
            final Potion potion = Potion.fromItemStack(this.value);

            config.set(path + ".potionType", potion.getType().name());
            config.set(path + ".potionSplash", potion.isSplash());
            config.set(path + ".potionLevel", potion.getLevel());
        }

        if (!this.value.getEnchantments().isEmpty()){
            final AtomicInteger id = new AtomicInteger(0);

            this.value.getEnchantments().forEach((enchantment, level) ->{

                config.set(path + ".enchantments." + id + ".enchantment", enchantment.getName());
                config.set(path + ".enchantments." + id + ".level", level);

                id.incrementAndGet();
            });
        }

        return this;
    }
}
