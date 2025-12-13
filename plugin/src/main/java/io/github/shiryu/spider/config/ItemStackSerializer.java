package io.github.shiryu.spider.config;

import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.ProfileInputType;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import com.google.common.collect.Lists;
import dev.lone.itemsadder.api.CustomStack;
import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.impl.Section;
import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import io.github.shiryu.spider.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ItemStackSerializer implements ConfigSerializer<ItemStack> {

    @Override
    public @NonNull ItemStack get(@NotNull Config config, @NotNull String path) {
        final String text = config.getOrSet(path + ".material", "PAPER");

        ItemBuilder builder;

        if (text.startsWith("items_adder:")){
            final String id = text.replace("items_adder:", "");
            final CustomStack custom = CustomStack.getInstance(id);

            if (custom == null)
                return ItemBuilder.from(Material.PAPER)
                        .build();

            builder = ItemBuilder.from(custom.getItemStack());
        } else {
            final Material material = Material.getMaterial(text.toUpperCase());

            if (material == null)
                return ItemBuilder.from(Material.PAPER)
                        .build();

            builder = ItemBuilder.from(material);
        }

        if (config.contains(path + ".amount"))
            builder.amount(config.getOrSet(path + ".amount", 1));

        if (config.contains(path + ".name"))
            builder.name(config.getOrSet(path + ".name", ""));

        if (config.contains(path + ".lore"))
            builder.lore(config.getOrSet(path + ".lore", Lists.newArrayList()));

        if (config.get("custom_model_data") != null){
            final int customModelData = config.getOrSet(path + ".custom-model-data", 0);

            builder.updateFunctional(meta -> {
                meta.setCustomModelData(customModelData);

                return meta;
            });
        }

        if (text.startsWith("LEATHER_")){
            Color color = Color.fromRGB((
                            config.getOrSet(path + ".color.red", 0)),
                    config.getOrSet(path + ".color.green", 0),
                    config .getOrSet(path + ".color.blue", 0));

            builder.dye(color);
        }

        if (text.equals("PLAYER_HEAD")){
            builder = ItemBuilder.from(XSkull.of(builder.build())
                    .profile(Profileable.of(ProfileInputType.TEXTURE_URL, config.getOrSet(path + ".skull_owner", "")))
                    .apply());
        }

        final Section enchantments = config.getSection(path + ".enchantments");

        if (enchantments != null){

            for (final String key : enchantments.getKeys()){
                final Enchantment enchantment = Enchantment.getByName(config.getOrSet(path + ".enchantments." + key + ".enchantment", "PROTECTION_ENVIRONMENTAL"));
                final int level = config.getOrSet(path + ".enchantments." + key + ".level", 0);

                if (enchantment == null)
                    continue;

                builder.enchant(enchantment, level);
            }
        }

        return builder.build();
    }

    @Override
    public void set(@NotNull Config config, @NotNull String path, @NonNull ItemStack item) {
        String material = item.getType().name();

        if (CustomStack.byItemStack(item) != null)
            material = "items_adder:" + CustomStack.byItemStack(item).getId();

        config.set(path + ".material", material);
        config.set(path + ".amount", item.getAmount());

        final ItemMeta meta = item.getItemMeta();

        if (meta != null){
            if (meta.hasDisplayName())
                config.set(path + ".name", meta.getDisplayName());

            if (meta.hasLore())
                config.set(path + ".lore", meta.getLore());

            if (meta.hasCustomModelData())
                config.set(path + ".custom-model-data", meta.getCustomModelData());

            if (item.getType().name().startsWith("LEATHER_")){
                final Color color = meta instanceof LeatherArmorMeta leatherMeta
                        ? leatherMeta.getColor()
                        : Color.WHITE;

                config.set(path + ".color.red", color.getRed());
                config.set(path + ".color.green", color.getGreen());
                config.set(path + ".color.blue", color.getBlue());
            }
        }

        item.getEnchantments().forEach((enchantment, level) ->{
            final String enchantmentPath = path + ".enchantments.";
            config.set(enchantmentPath + ".enchantment", enchantment.getKey().getKey());
            config.set(enchantmentPath + ".level", level);
        });
    }
}
