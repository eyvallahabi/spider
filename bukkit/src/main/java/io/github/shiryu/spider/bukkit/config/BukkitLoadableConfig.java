package io.github.shiryu.spider.bukkit.config;

import io.github.shiryu.spider.api.config.LoadableConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class BukkitLoadableConfig implements LoadableConfig {

    private final Plugin plugin;
    private final String name;
    private final File file;

    private String path;

    private BukkitConfig config;

    public BukkitLoadableConfig(@NotNull final Plugin plugin,
                                @NotNull final String name,
                                @NotNull final String path){
        this.plugin = plugin;
        this.name = name;
        this.path = path;

        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + path + "/" + name);
    }

    public BukkitLoadableConfig(@NotNull final Plugin plugin, @NotNull final String name){
        this.plugin = plugin;
        this.name = name;

        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + name);
    }

    @Override
    public void create() {
        if (this.file.exists()){
            this.config = new BukkitConfig(this.file);

            return;
        }

        if (!this.file.getParentFile().exists())
            this.file.getParentFile().mkdirs();

        if (this.path == null){
            this.copy(
                    this.plugin.getResource(this.name),
                    this.file
            );
        }else{
            this.copy(
                    this.plugin.getResource(this.path.replaceAll("/", "") + "/" + this.name),
                    this.file
            );
        }

        try{
            this.file.createNewFile();
        }catch (final Exception exception){

        }
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
    public @NotNull String getResourcePath() {
        return this.path;
    }

    @Override
    public void save() {
        if (this.path == null){
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
}

