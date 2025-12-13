package io.github.shiryu.spider.api.storage.connection;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface StorageConnection {

    @NotNull
    ConnectionType getType();

    void connect(@NotNull final Plugin plugin);

    void disconnect();

    boolean isConnected();

}
