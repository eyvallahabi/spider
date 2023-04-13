package io.github.shiryu.spider;

import com.google.gson.Gson;
import io.github.shiryu.spider.event.listener.ArmorListener;
import io.github.shiryu.spider.util.Listeners;
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

        Listeners.register(
                this,
                new ArmorListener()
        );
    }

    @Override
    public void onDisable() {

    }
}
