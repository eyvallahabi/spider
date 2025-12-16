package io.github.shiryu.spider.api.config;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.config.impl.BasicConfig;
import io.github.shiryu.spider.api.config.impl.ResourceConfig;
import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

@UtilityClass
public class Configs {

    private final Map<Class<?>, ConfigSerializer<?>> serializers = Maps.newHashMap();

    public <T> void register(@NotNull final Class<T> clazz, @NotNull final ConfigSerializer<T> serializer){
        serializers.put(clazz, serializer);
    }

    @Nullable
    public <T> ConfigSerializer<T> getSerializer(@NotNull final Class<T> clazz){
        //noinspection unchecked
        return (ConfigSerializer<T>) serializers.get(clazz);
    }

    @NotNull
    public Config create(@NotNull final File file){
        return new BasicConfig(file);
    }

    @NotNull
    public Config create(@NotNull final Plugin plugin, @NotNull final String name, @NotNull final String path, final boolean copyDefaults){
        final Config config;

        if (copyDefaults){
            config = new ResourceConfig(plugin, name + ".yml", path);
        }else{
            if (path.isEmpty())
                config = new BasicConfig(new File(plugin.getDataFolder(), name + ".yml"));
            else
                config = new BasicConfig(new File(plugin.getDataFolder(), path + File.separator + name + ".yml"));
        }

        config.create();
        config.load();

        return config;
    }

    @NotNull
    public Config create(@NotNull final Plugin plugin, @NotNull final String name, final boolean copyDefaults){
        return create(plugin, name, "", copyDefaults);
    }

    @NotNull
    public Config create(@NotNull final Plugin plugin, @NotNull final String name, @NotNull final String path){
        return create(plugin, name, path, false);
    }

    @NotNull
    public Config create(@NotNull final Plugin plugin, @NotNull final String name){
        return create(plugin, name, "");
    }
}
