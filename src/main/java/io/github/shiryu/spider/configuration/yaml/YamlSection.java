package io.github.shiryu.spider.configuration.yaml;

import io.github.shiryu.spider.configuration.Config;
import io.github.shiryu.spider.configuration.ConfigSection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class YamlSection implements ConfigSection {

    private final ConfigurationSection section;


    @Override
    public @NotNull <T> Optional<T> get(@NotNull String path) {
        if (this.section == null) return Optional.empty();

        T value = (T) this.section.get(path);

        if (value == null) return Optional.empty();

        return Optional.ofNullable(value);
    }

    @Override
    public <T> T getOrSet(@NotNull String path, @NotNull T fallback) {
        if (fallback == null) return null;
        if (this.section == null) load();

        final T get = (T) get(path).orElse(null);

        if (get == null){
            set(path, fallback);

            return fallback;
        }

        return get;
    }

    @Override
    public @NotNull Optional<ConfigSection> getSection(@NotNull String path) {
        return Optional.ofNullable(
                new YamlSection(
                        this.section.getConfigurationSection(path)
                )
        );
    }

    @Override
    public <T> void set(@NotNull String path, @NotNull T object) {
        section.set(path, object);
    }


    @Override
    public @NotNull String getName() {
        return section.getName();
    }

    @Override
    public @NotNull Optional<Config> findFile(@NotNull String name) {
        return Optional.empty();
    }

    @Override
    public @NotNull String getPath() {
        return section.getCurrentPath();
    }
    @Override
    public @NotNull File getFile() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }


    @Override
    public void forEach(@NotNull String path, @NotNull Consumer<Config> consumer) {

    }
}
