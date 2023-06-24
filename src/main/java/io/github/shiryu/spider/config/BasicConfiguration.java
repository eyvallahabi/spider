package io.github.shiryu.spider.config;

import io.github.shiryu.spider.config.serializer.ConfigSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

public class BasicConfiguration {

    private final BasicFile file;

    private FileConfiguration configuration;

    public BasicConfiguration(@NotNull final BasicFile file){
        this.file = file;
    }

    public BasicConfiguration load(){
        this.configuration = YamlConfiguration.loadConfiguration(this.file.asFile().get());

        return this;
    }

    public BasicConfiguration save(){
        final File asFile = this.file.asFile().orElse(null);

        if (asFile != null){
            try{
                configuration.save(asFile);
                configuration.load(asFile);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return this;
    }

    public <T> void set(@NotNull final String path, @NotNull final T object, @NotNull final ConfigSerializer<T> serializer){
        serializer.write(this, object, path);
    }

    public void set(@NotNull final String path, @NotNull final Object object){
        this.configuration.set(path, object);

        save();
    }

    public Optional<BasicSection> getSection(@NotNull final String path){
        final ConfigurationSection section = configuration.getConfigurationSection(path);

        if (configuration == null || section == null) return Optional.empty();

        return Optional.of(
                new BasicSection(
                        this,
                        section
                )
        );
    }

    @NotNull
    public <T> Optional<T> get(@NotNull final String path){
        if (this.configuration == null) return Optional.empty();

        T value = (T) this.configuration.get(path);

        if (value == null) return Optional.empty();

        return Optional.ofNullable(value);
    }

    @NotNull
    public <T> T get(@NotNull final String path, @NotNull final ConfigSerializer<T> serializer){
        return serializer.read(this, path);
    }

    public <T> T getOrSet(@NotNull final String path, @NotNull final T fallback){
        if (fallback == null) return null;
        if (this.configuration == null) load();

        final T get = (T) get(path).orElse(null);

        if (get == null){
            set(path, fallback);

            return fallback;
        }

        return get;
    }

    @NotNull
    public FileConfiguration toBukkit(){
        return this.configuration;
    }
}
