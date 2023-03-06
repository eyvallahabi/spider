package io.github.shiryu.spider.util.item;

import com.cryptomorin.xseries.XMaterial;
import io.github.shiryu.spider.util.Colored;
import io.github.shiryu.spider.util.version.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.map.MapView;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemBuilder {

    @NotNull
    public static ItemBuilder empty(){
        return from(XMaterial.AIR);
    }

    @NotNull
    public static ItemBuilder from(@NotNull final XMaterial material){
        return from(material.parseItem());
    }

    @NotNull
    public static ItemBuilder from(@NotNull final Material material){
        return from(XMaterial.matchXMaterial(material).parseItem());
    }

    @NotNull
    public static ItemBuilder from(@NotNull final ItemStack item){
        return new ItemBuilder(new ItemStack(item), item.getItemMeta());
    }

    private final ItemMeta meta;

    private ItemStack base;

    public ItemBuilder(@NotNull final ItemStack base, @NotNull final ItemMeta meta){
        this.base = base;
        this.meta = meta;
    }

    @NotNull
    public ItemStack build(){
        return this.base;
    }

    @NotNull
    public ItemBuilder skull(@NotNull final UUID uuid){
        updateItem(item ->{
            item.setType(XMaterial.PLAYER_HEAD.parseMaterial());

            return item;
        });

        updateFunctional(meta ->{
            final SkullMeta skullMeta = (SkullMeta) meta;

            skullMeta.setOwner(Bukkit.getOfflinePlayer(uuid).getName());

            return skullMeta;
        });

        return this;
    }

    @NotNull
    public ItemBuilder spawnEgg(@NotNull final EntityType type){
        updateFunctional(meta ->{
            final SpawnEggMeta spawnEggMeta = (SpawnEggMeta) meta;

            spawnEggMeta.setSpawnedType(type);

            return spawnEggMeta;
        });

        return this;
    }

    @NotNull
    public ItemBuilder map(@NotNull final MapView view){
        if (ServerVersion.isServerVersionBelow(ServerVersion.V1_13)){
            return this.updateItem(item ->{
                try{
                    final Class<?> mapView = view.getClass();

                    final Method getId = mapView.getMethod("getId");

                    item.setDurability((short)getId.invoke(view, null));
                }catch (final Exception e){
                    e.printStackTrace();

                    return item;
                }

                return item;
            });
        }

        return this.updateFunctional(meta ->{
            final MapMeta mapMeta = (MapMeta) meta;

            mapMeta.setMapView(view);
            mapMeta.setMapId(view.getId());

            return meta;
        });
    }

    @NotNull
    public ItemBuilder map(@NotNull final World world){
        return this.map(Bukkit.createMap(world));
    }

    @NotNull
    public ItemBuilder durability(final int durability){
        return this.updateItem(item ->{
            item.setDurability((short)durability);

            return item;
        });
    }

    @NotNull
    public ItemBuilder type(@NotNull final XMaterial material){
        return this.updateItem(itemStack ->{
            itemStack = material.parseItem();

            itemStack.setItemMeta(this.meta);

            return itemStack;
        });
    }

    @NotNull
    public final ItemBuilder enchant(@NotNull final Enchantment enchantment, final int level){
        return this.updateItem(item ->{
            item.addUnsafeEnchantment(enchantment, level);

            return item;
        });
    }

    @NotNull
    public final ItemBuilder potion(@NotNull final PotionType type, final boolean splash, final int level){
        return this.updateItem(item ->{
            final Potion potion = new Potion(type, level);

            potion.setSplash(splash);

            return potion.toItemStack(item.getAmount());
        });
    }
    @NotNull
    public final ItemBuilder name(@NotNull final String name){
        return this.update(itemMeta ->
                itemMeta.setDisplayName(Colored.convert(name)));
    }

    @NotNull
    public final ItemBuilder updateName(@NotNull final Function<String, String> function){
        return this.update(itemMeta ->{
            if (itemMeta.getDisplayName() == null || !(itemMeta.hasDisplayName())) itemMeta.setDisplayName(Colored.convert(function.apply("")));

            itemMeta.setDisplayName(Colored.convert(function.apply(itemMeta.getDisplayName())));
        });
    }
    @NotNull
    public final ItemBuilder updateLore(@NotNull final Function<List<String>, List<String>> function){
        return this.update(itemMeta ->{
            if (!(itemMeta.hasLore())) itemMeta.setLore(Colored.convert(function.apply(new ArrayList<>())));

            itemMeta.setLore(Colored.convert(function.apply(itemMeta.getLore())));
        });
    }

    @NotNull
    public final ItemBuilder lore(@NotNull final List<String> lore){
        return this.update(itemMeta ->
                itemMeta.setLore(Colored.convert(lore)));
    }

    @NotNull
    public final ItemBuilder lore(@NotNull final String... lore){
        return this.lore(
                Arrays.asList(lore)
        );
    }

    @NotNull
    public final ItemBuilder lore(@NotNull final String lore){
        return this.update(itemMeta ->{
            if (!(itemMeta.hasLore())) itemMeta.setLore(Collections.singletonList(lore));

            final List<String> itemLore = itemMeta.getLore();

            itemLore.add(lore);

            itemMeta.setLore(
                    Colored.convert(itemLore)
            );
        });
    }

    @NotNull
    public final ItemBuilder amount(final int amount){
        return this.updateItem(itemStack ->{
            itemStack.setAmount(amount);

            return itemStack;
        });
    }

    @NotNull
    public final ItemBuilder data(final byte data) {
        return this.updateItemBuilder(itemStack ->{
            final MaterialData materialData = itemStack.getData();

            materialData.setData(data);

            return this.data(materialData);
        });
    }

    @NotNull
    public final ItemBuilder flags(@NotNull final ItemFlag... itemFlags){
        return this.update(itemMeta ->
            itemMeta.addItemFlags(itemFlags)
        );
    }

    @NotNull
    public final ItemBuilder unbreakable(final boolean unbreakable){
        return this.update(meta -> meta.setUnbreakable(unbreakable));
    }

    @NotNull
    public final ItemBuilder dye(@NotNull final Color color){
        return this.updateItem(item ->{
            if (item.getType().name().startsWith("LEATHER_")){
                final LeatherArmorMeta armorMeta = (LeatherArmorMeta) item.getItemMeta();

                armorMeta.setColor(color);

                item.setItemMeta(armorMeta);
            }

            return item;
        });
    }
    @NotNull
    public final ItemBuilder data(@NotNull final MaterialData data) {
       return this.updateItem(itemStack ->{
           itemStack.setData(data);

           return itemStack;
       });
    }

    @NotNull
    public final ItemBuilder damage(final short damage) {
        return this.updateItem(itemStack ->{
            itemStack.setDurability(damage);

            return itemStack;
        });
    }

    @NotNull
    public final ItemBuilder enchantIf(@NotNull final int level, @NotNull final Enchantment enchantment, final boolean why){
        if (!why) return this;

        updateItem(item -> {
            item.addEnchantment(enchantment, level);

            return item;
        });

        return this;
    }

    @NotNull
    public ItemBuilder updateItemBuilder(@NotNull final Function<ItemStack, ItemBuilder> function){
        return function.apply(this.base);
    }

    @NotNull
    public final ItemBuilder updateItem(@NotNull final Function<ItemStack, ItemStack> function){
        this.base = function.apply(this.base);

        return this;
    }

    @NotNull
    public final ItemBuilder updateFunctional(@NotNull final Function<ItemMeta, ItemMeta> function){
        this.base.setItemMeta(function.apply(this.meta));

        return this;
    }

    @NotNull
    private final ItemBuilder update(@NotNull final Consumer<ItemMeta> consumer){
        consumer.accept(this.meta);

        this.base.setItemMeta(this.meta);

        return this;
    }
}
