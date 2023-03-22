package io.github.shiryu.spider.config;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class BasicFile {

    private final String name;
    private final String path;

    private Plugin plugin;
    private File file;
    private boolean loadDefaults = false;

    public BasicFile(@NotNull final String name, @NotNull final String path){
        this.name = name;
        this.path = path;
    }

    public BasicFile(@NotNull final String name){
        this(name, "");
    }

    public void delete(){
        this.file.delete();
    }

    @NotNull
    public BasicFile loadDefaults(final boolean loadDefaults){
        this.loadDefaults = loadDefaults;

        return this;
    }

    @NotNull
    public BasicFile handle(@NotNull final Consumer<BasicFile> consumer){
        consumer.accept(this);

        return this;
    }

    @NotNull
    public BasicFile notExists(@NotNull final Consumer<BasicFile> consumer){
        if (file.exists()) return this;

        consumer.accept(this);

        return this;
    }

    @NotNull
    public BasicFile exists(@NotNull final Consumer<BasicFile> consumer){
        if (!file.exists()) return this;

        consumer.accept(this);

        return this;
    }

    @NotNull
    public BasicFile create(@NotNull final Plugin plugin, final boolean mkdir){
        this.plugin = plugin;

        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + this.path +  this.name + "/");

        if (mkdir && !file.exists()){
            file.mkdirs();

            return this;
        }

        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

        if (file.exists()){
            config().load();

            return this;
        }

        if (this.loadDefaults && !file.exists()){
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

    @NotNull
    public String name(){
        final AtomicReference<String> name = new AtomicReference<>("");

        asFile().ifPresent(x -> name.set(x.getName().replaceAll(".yml", "")));

        return name.get();
    }

    @NotNull
    public Optional<BasicFile> findFile(@NotNull final String name){
        if (this.file == null || this.file.listFiles() == null) return Optional.empty();

        for (File file : this.file.listFiles()){
            if (file.getName() == name){
                return Optional.of(
                        new BasicFile(
                                name,
                                this.file.getPath()
                        )
                );
            }
        }

        return Optional.empty();
    }

    public void forEach(@NotNull final String path, @NotNull final Consumer<BasicFile> consumer){
        if (this.file == null || this.file.listFiles() == null) return;

        for (File file : this.file.listFiles())
            consumer.accept(
                    new BasicFile(
                            file.getName(),
                            path
                    )
                    .create(plugin, false)
            );
    }

    @NotNull
    public BasicConfiguration config(){
        return new BasicConfiguration(this).load();
    }

    @NotNull
    public Optional<File> asFile(){
        return Optional.ofNullable(
                this.file
        );
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
