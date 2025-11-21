package io.github.shiryu.spider.bukkit.config;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.Section;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import io.github.shiryu.spider.api.config.item.impl.EmptyConfigItem;
import io.github.shiryu.spider.api.config.item.impl.GeneralConfigItem;
import io.github.shiryu.spider.bukkit.config.item.ItemStackConfigItem;
import io.github.shiryu.spider.bukkit.config.item.LocationConfigItem;
import io.github.shiryu.spider.bukkit.config.item.PotionEffectConfigItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class BukkitSection implements Section {

    private final Config parent;
    private final ConfigurationSection section;

    @Override
    public void create() {

    }

    @Override
    public void save() {

    }

    @Override
    public boolean has(@NotNull String path) {
        return this.section.contains(path);
    }

    @Override
    public @Nullable Section getSection(@NotNull String path) {
        final ConfigurationSection section = this.section.getConfigurationSection(path);

        if (section == null)
            return null;

        return new BukkitSection(this.parent, section);
    }

    @Override
    public void set(@NotNull String path, @NotNull Object object) {
        this.section.set(
                path,
                object
        );
    }

    @Override
    public <T> T get(@NotNull String path) {
        return (T) this.section.get(path);
    }

    @Override
    public @NotNull <T> ConfigItem<T> getItem(@NotNull String path, @NotNull Class<T> clazz) {
        if (clazz.equals(Location.class))
            return (ConfigItem<T>) new LocationConfigItem();

        if (clazz.equals(PotionEffect.class))
            return (ConfigItem<T>) new PotionEffectConfigItem();

        if (clazz.equals(ItemStack.class)){
            final ItemStackConfigItem item = new ItemStackConfigItem();

            item.get(this, path);

            return (ConfigItem<T>) item;
        }

        return new GeneralConfigItem<>();
    }

    @Override
    public @Nullable File getFile() {
        return this.parent.getFile();
    }

    @Override
    public @NotNull Set<String> getKeys() {
        return this.section.getKeys(false);
    }
}
