package io.github.shiryu.spider.api.config;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.config.impl.BasicConfig;
import io.github.shiryu.spider.api.config.impl.ResourceConfig;
import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

public class Configs {

    @NotNull
    public static Configs from(@NotNull final Plugin plugin){
        return new Configs(plugin);
    }

    private final Plugin plugin;

    private String name;
    private String path = "";
    private boolean defaults = false;

    private final Map<Class<?>, ConfigSerializer<?>> serializers = Maps.newHashMap();

    private Configs(@NotNull final Plugin plugin){
        this.plugin = plugin;
    }

    @NotNull
    public Configs with(@NotNull final String name){
        this.name = name;
        return this;
    }

    @NotNull
    public Configs with(@NotNull final Class<?> clazz, @NotNull final ConfigSerializer<?> serializer){
        this.serializers.put(clazz, serializer);
        return this;
    }

    @NotNull
    public Configs at(@NotNull final String path){
        this.path = path;
        return this;
    }

    @NotNull
    public Configs defaults(){
        this.defaults = true;
        return this;
    }

    @NotNull
    public Config create(){
        final Config config;

        if (this.defaults){
            config = new ResourceConfig(this.plugin, this.name + ".yml", this.path, this.serializers);
        }else{
            if (path.isEmpty())
                config = new BasicConfig(new File(this.plugin.getDataFolder(), this.name + ".yml"), this.serializers);
            else
                config = new BasicConfig(new File(this.plugin.getDataFolder(), this.path + File.separator + this.name + ".yml"), this.serializers);
        }

        config.create();
        config.load();

        return config;
    }
}
