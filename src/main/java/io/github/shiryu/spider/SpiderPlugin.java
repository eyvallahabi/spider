package io.github.shiryu.spider;

import com.google.gson.Gson;
import io.github.shiryu.spider.storage.annotations.Entity;
import io.github.shiryu.spider.util.location.gson.LocationTypeFactory;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SpiderPlugin extends JavaPlugin {

    @Getter
    private static SpiderPlugin plugin;

    private Gson gson;

    @Override
    public void onEnable() {
        plugin = this;

        this.gson = new Gson()
                .newBuilder()
                .registerTypeAdapterFactory(new LocationTypeFactory())
                .create();
    }

    @Override
    public void onDisable() {

    }
}
