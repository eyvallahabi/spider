package io.github.shiryu.spider.api.storage.connection.impl.sql;

import io.github.shiryu.spider.api.storage.connection.ConnectionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.DriverManager;

@Getter
@RequiredArgsConstructor
public class SQLiteConnection extends AbstractSQLConnection {

    private final File file;

    @Override
    public @NotNull ConnectionType getType() {
        return ConnectionType.SQLITE;
    }

    @Override
    public void connect(@NotNull Plugin plugin) {
        try{
            if (!file.exists()){
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
            }

            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file.getAbsolutePath());
        }catch (final Exception exception){
            exception.printStackTrace();
        }
    }
}
