package io.github.shiryu.spider.bukkit.config;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.Section;
import io.github.shiryu.spider.api.config.item.ConfigItem;
import io.github.shiryu.spider.api.config.item.impl.EmptyConfigItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
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
    public boolean has(@NotNull String path) {
        return this.section.contains(path);
    }

    @Override
    public @Nullable Section getSection(@NotNull String path) {
        return new BukkitSection(this.parent, this.section.getConfigurationSection(path));
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
    public @NotNull <T> ConfigItem<T> getItem(@NotNull String path) {
        return new EmptyConfigItem<>();
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
