package io.github.shiryu.spider.api.storage.connection.impl;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.storage.connection.ConnectionType;
import io.github.shiryu.spider.api.storage.connection.StorageConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class FlatFileConnection implements StorageConnection {

    private final String name;

    private Plugin plugin;

    private final Map<UUID, File> files = Maps.newHashMap();

    @Override
    public @NotNull ConnectionType getType() {
        return ConnectionType.FLAT_FILE;
    }

    @NotNull
    public File create( @NotNull final UUID uuid) {
        final File parent = new File(this.plugin.getDataFolder(), this.name + "/");

        if (!parent.exists())
            parent.mkdirs();

        final File file = new File(parent, uuid.toString() + ".yml");

        try{
            if (file.createNewFile()){
                this.files.put(uuid, file);

                return file;
            }
        }catch (final Exception e){
            e.printStackTrace();
        }

        return file;
    }

    @Override
    public void connect(@NotNull final Plugin plugin) {
        this.plugin = plugin;

        final File parent = new File(plugin.getDataFolder(), this.name + "/");

        if (!parent.exists())
            parent.mkdirs();

        final File[] files = parent.listFiles();

        if (files == null)
            return;

        for (final File file : files){
            final String name = file.getName().replace(".yml", "");

            try{
                final UUID uuid = UUID.fromString(name);
                this.files.put(uuid, file);
            }catch (final Exception ignored){

            }
        }
    }

    @Override
    public void disconnect() {
        this.files.clear();
    }

    @Override
    public boolean isConnected() {
        return !this.files.isEmpty();
    }
}
