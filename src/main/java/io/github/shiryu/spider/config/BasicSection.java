package io.github.shiryu.spider.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class BasicSection {

    private final BasicConfiguration configuration;
    private final ConfigurationSection section;

    public BasicSection(@NotNull final BasicConfiguration configuration, @NotNull final ConfigurationSection section){
        this.configuration = configuration;
        this.section = section;
    }

    public void set(@NotNull final String path, @NotNull final Object object){
        this.section.set(path, object);

        this.configuration.save();
    }

    public Optional<BasicSection> getSection(@NotNull final String path){
        return configuration.getSection(path);
    }

    public ItemStack getItemStack(@NotNull final String path){
        return this.configuration.getItemStack(this.section.getName() + "." + path);
    }

    public <T> Optional<T> get(@NotNull final String path){
        if (this.configuration == null) return Optional.empty();

        return Optional.ofNullable(
                (T) this.section.get(path)
        );
    }

    public <T> T getOrSet(@NotNull final String path, @NotNull final T fallback){
        if (fallback == null) return null;

        final T get = (T) get(path).orElse(null);

        if (get == null){
            set(path, fallback);

            return fallback;
        }

        return get;
    }

    public void forEach(@NotNull final Consumer<String> consumer){
        if (section.getKeys(false) == null) return;

        section.getKeys(false).forEach(key -> consumer.accept(key));
    }

    @NotNull
    public ConfigurationSection asBukkit(){
        return this.section;
    }
}