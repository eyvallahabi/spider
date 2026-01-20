package io.github.shiryu.spider.config;

import io.github.shiryu.spider.api.config.serializer.ConfigSerializer;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@UtilityClass
public class ConfigSerializers {

    public static final ConfigSerializer<ItemStack> ITEM_STACK = new ItemStackSerializer();
    public static final ConfigSerializer<Location> LOCATION = new LocationSerializer();

    @NotNull
    public static Map<Class<?>, ConfigSerializer<?>> defaultSerializers() {
        return Map.of(
                ItemStack.class, ITEM_STACK,
                Location.class, LOCATION
        );
    }
}
