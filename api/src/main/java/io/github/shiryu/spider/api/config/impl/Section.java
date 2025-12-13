package io.github.shiryu.spider.api.config.impl;

import io.github.shiryu.spider.api.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Set;
import java.util.function.Consumer;

public record Section(Config parent, ConfigurationSection section) implements Config {

    @Override
    public @NotNull String getName() {
        return this.section.getName();
    }

    @Override
    public @Nullable String getPath() {
        return this.section.getCurrentPath();
    }

    @Override
    public boolean contains(@NotNull String path) {
        return this.section.contains(path);
    }

    @Override
    public void set(@NotNull String path, Object value) {
        //TODO ADD CONVERTERS INTO THIS
        this.section.set(path, value);
    }

    @Override
    public <T> T get(@NotNull String path) {
        return (T) this.section.get(path);
    }

    @Override
    public <T> T get(@NotNull String path, @NotNull Class<T> clazz) {
        //TODO ADD CONVERTERS INTO THIS

        return null;
    }

    @Override
    public @NotNull Section getSection(@NotNull String path) {
        return new Section(this, this.section.getConfigurationSection(path));
    }

    @NotNull
    public Set<String> getKeys(){
        return this.section.getKeys(false);
    }

    public void forEach(@NotNull final Consumer<String> consumer){
        this.section.getKeys(false).forEach(consumer);
    }

    @Override
    public boolean create() {
        return this.parent.create();
    }

    @Override
    public void load() {
        this.parent.load();
    }

    @Override
    public void save() {
        this.parent.save();
    }

    @Override
    public @Nullable File getFile() {
        return this.parent.getFile();
    }
}
