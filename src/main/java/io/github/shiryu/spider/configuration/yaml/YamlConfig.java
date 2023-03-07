package io.github.shiryu.spider.configuration.yaml;

import io.github.shiryu.spider.configuration.Config;
import io.github.shiryu.spider.configuration.ConfigSection;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Getter
public class YamlConfig implements Config {

    private final String name;
    private final String path;

    private File file;
    private YamlConfiguration configuration;
    private Plugin plugin;

    public YamlConfig(@NotNull final String name, @NotNull final String path){
        this.name = name;
        this.path = path;
    }

    @Override
    public @NotNull <T> Optional<T> get(@NotNull String path) {
        if (this.configuration == null) return Optional.empty();

        T value = (T) this.configuration.get(path);

        if (value == null) return Optional.empty();

        return Optional.ofNullable(value);
    }

    @Override
    public <T> @NotNull T getOrSet(@NotNull String path, @NotNull T fallback) {
        if (fallback == null) return null;
        if (this.configuration == null) load();

        final T get = (T) get(path).orElse(null);

        if (get == null){
            set(path, fallback);

            return fallback;
        }

        return get;
    }

    @Override
    public @NotNull Optional<ConfigSection> getSection(@NotNull String path) {
        return Optional.empty();
    }

    @Override
    public <T> void set(@NotNull String path, @NotNull T object) {
        this.configuration.set(path, object);

        this.save();
    }

    @Override
    public Optional<Config> findFile(@NotNull final String name){
        if (this.file == null || this.file.listFiles() == null) return Optional.empty();

        for (File file : this.file.listFiles()){
            if (file.getName() == name){
                return Optional.of(
                        new YamlConfig(
                                name,
                                this.file.getPath()
                        ).create(this.plugin, false, false)
                );
            }
        }

        return Optional.empty();
    }

    @Override
    public void forEach(@NotNull final String path, @NotNull final Consumer<Config> consumer){
        if (this.file == null || this.file.listFiles() == null) return;

        for (File file : this.file.listFiles())
            consumer.accept(
                    new YamlConfig(file.getName(), path)
                            .create(this.plugin, false, false)
            );
    }

    @NotNull
    public YamlConfig create(@NotNull final Plugin plugin, final boolean defaults, final boolean mkdir){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + this.path + this.name + "/");

        if (mkdir && !file.exists()){
            file.mkdirs();

            return this;
        }

        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

        if (file.exists()){


            return this;
        }

        if (defaults && !file.exists()){
            copy(
                    plugin.getResource(this.name),
                    this.file
            );

            return this;
        }

        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public void save(){
        try{
            this.configuration.save(this.file);
        }catch (final Exception exception){

        }
    }

    @NotNull
    public void load(){
        this.configuration = YamlConfiguration.loadConfiguration(Objects.requireNonNull(this.file));
    }

    private void copy(@NotNull final InputStream inputStream, @NotNull final File file) {
        try (OutputStream out = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];

            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            inputStream.close();
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

}
