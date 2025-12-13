package io.github.shiryu.spider.api.config.impl;

import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.Configs;
import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class BasicConfig implements Config {

    private final File file;
    private YamlConfiguration configuration;

    @Override
    public @NotNull String getName() {
        return this.file.getName().replaceAll(".yml", "");
    }

    @Override
    public @Nullable String getPath() {
        return this.file.getPath();
    }

    @Override
    public boolean create() {
        if (!this.file.getParentFile().exists())
            this.file.getParentFile().mkdirs();

        if (this.file.exists()){
            this.configuration = YamlConfiguration.loadConfiguration(this.file);
        } else {
            try {
                if (this.file.createNewFile())
                    this.configuration = YamlConfiguration.loadConfiguration(this.file);
            } catch (final Exception exception){
                throw new RuntimeException("Could not create file " + this.file.getName(), exception);
            }
        }

        return true;
    }

    @Override
    public void load() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void save() {
        try {
            this.configuration.save(this.file);
            this.configuration.load(this.file);
        } catch (final Exception exception){
            throw new RuntimeException("Could not save file " + this.file.getName(), exception);
        }
    }

    @Override
    public boolean contains(@NotNull String path) {
        return this.configuration.contains(path);
    }

    @Override
    public void set(@NotNull String path, Object value) {
        final ConfigSerializer serializer = Configs.getSerializer(value.getClass());

        if (serializer != null){
            serializer.set(this, path, value);
            return;
        }

        this.configuration.set(path, value);
    }

    @Override
    public <T> T get(@NotNull String path) {
        return (T) this.configuration.get(path);
    }

    @Override
    public <T> T get(@NotNull String path, @NotNull Class<T> clazz) {
        final ConfigSerializer<T> serializer = Configs.getSerializer(clazz);

        if (serializer == null)
            return null;

        return serializer.get(this, path);
    }

    @Override
    public @Nullable Section getSection(@NotNull String path) {
        final ConfigurationSection section = this.configuration.getConfigurationSection(path);

        if (section == null)
            return null;

        return new Section(this, section);
    }
}
