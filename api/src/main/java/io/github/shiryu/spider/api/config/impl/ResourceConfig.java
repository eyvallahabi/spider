package io.github.shiryu.spider.api.config.impl;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.config.Config;
import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Getter
public class ResourceConfig implements Config {

    private final Plugin plugin;
    private final String name;
    private final String path;

    private final File file;

    private final Map<Class<?>, ConfigSerializer<?>> serializers;

    private BasicConfig config;

    public ResourceConfig(@NotNull final Plugin plugin,
                          @NotNull final String name,
                          @NotNull final String path,
                          @NotNull final Map<Class<?>, ConfigSerializer<?>> serializers) {
        this.plugin = plugin;
        this.name = name;
        this.path = path;
        this.serializers = Maps.newHashMap(serializers);

        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + path + "/" + name);
    }

    public ResourceConfig(@NotNull final Plugin plugin,
                          @NotNull final String name,
                          @NotNull final Map<Class<?>, ConfigSerializer<?>> serializers) {
        this(plugin, name, "", serializers);
    }

    public ResourceConfig(@NotNull final Plugin plugin,
                          @NotNull final String name){
        this(plugin, name, "", Maps.newHashMap());
    }

    public ResourceConfig(@NotNull final Plugin plugin,
                          @NotNull final String name,
                          @NotNull final String path){
        this(plugin, name, path, Maps.newHashMap());
    }

    @Override
    public boolean create() {
        if (this.file.exists()){
            this.config = new BasicConfig(this.file, this.serializers);
            this.config.load();

            return true;
        }

        if (!this.file.getParentFile().exists())
            this.file.getParentFile().mkdirs();

        final InputStream stream;

        if (this.path == null || path.isEmpty()){
            stream = this.plugin.getResource(this.name);
        }else{
            stream = this.plugin.getResource(this.path.replaceAll("/", "") + "/" + this.name);
        }

        if (stream == null)
            return false;

        this.copy(stream, this.file);

        this.config = new BasicConfig(this.file, this.serializers);
        this.config.load();

        return true;
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

    @Override
    public void load() {

    }

    @Override
    public void save() {
        if (this.path == null || this.path.isEmpty()){
            this.plugin.saveResource(
                    this.name,
                    true
            );
        }else{
            this.plugin.saveResource(
                    this.path.replaceAll("/", "") + "/" + this.name,
                    true
            );
        }
    }

    @Override
    public boolean contains(@NotNull String path) {
        return this.config.contains(path);
    }

    @Override
    public void set(@NotNull String path, Object value) {
        this.config.set(path, value);
    }

    @Override
    public <T> T get(@NotNull String path) {
        return this.config.get(path);
    }

    @Override
    public <T> T get(@NotNull String path, @NotNull Class<T> clazz) {
        return this.config.get(path, clazz);
    }

    @Override
    public @Nullable Section getSection(@NotNull String path) {
        return this.config.getSection(path);
    }
}
