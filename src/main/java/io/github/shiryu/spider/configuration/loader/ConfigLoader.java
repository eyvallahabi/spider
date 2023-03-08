package io.github.shiryu.spider.configuration.loader;

import io.github.shiryu.spider.configuration.Config;
import io.github.shiryu.spider.configuration.annotations.*;
import io.github.shiryu.spider.configuration.yaml.YamlConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

@UtilityClass
public class ConfigLoader {

    @NotNull
    public Config yaml(@NotNull final Plugin plugin, @NotNull final BaseConfiguration base){
        final Class<?> clazz = base.getClass();

        if (!clazz.isAnnotationPresent(ConfigHandler.class))
            throw new IllegalStateException("Every base configuration needs an ConfigHandler");

        final ConfigHandler handler = clazz.getAnnotation(ConfigHandler.class);

        final YamlConfig config;

        if (clazz.isAnnotationPresent(Path.class)){
            config = new YamlConfig(handler.name(), clazz.getAnnotation(Path.class).path());
        }else{
            config = new YamlConfig(handler.name());
        }

        config.create(plugin, clazz.isAnnotationPresent(LoadDefaults.class), clazz.isAnnotationPresent(Mkdir.class));
        config.load();

        for (final Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);

            final Path path = field.getAnnotation(Path.class);

            if (field.isAnnotationPresent(Value.class)) {
                final Value value = field.getAnnotation(Value.class);

                try{
                    field.set(
                            base,
                            config.getValue(path.path(), value.clazz())
                                    .load(config)
                                    .getValue()
                    );
                }catch (final Exception exception){
                    exception.printStackTrace();
                }
            }

            if (field.isAnnotationPresent(Section.class)){
                try{
                    field.set(
                            base,
                            config.getSection(path.path()).orElse(null)
                    );
                }catch (final Exception exception){
                    exception.printStackTrace();
                }
            }
        }

        config.save();
        config.load();

        return config;
    }
}
