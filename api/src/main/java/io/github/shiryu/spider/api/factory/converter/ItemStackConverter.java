package io.github.shiryu.spider.api.factory.converter;

import io.github.shiryu.spider.api.factory.FactoryConverter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ItemStackConverter implements FactoryConverter<ItemStack> {

    @Override
    public @NotNull String deserialize(@NonNull ItemStack input) {
        try{
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            final BukkitObjectOutputStream data = new BukkitObjectOutputStream(output);

            data.writeObject(input);
            data.close();

            return Base64.getEncoder().encodeToString(output.toByteArray());
        }catch (final Exception exception){
            throw new RuntimeException("Could not serialize ItemStack", exception);
        }
    }

    @Override
    public ItemStack serialize(@NotNull String input) {
        try{
            final ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(input));

            final BukkitObjectInputStream data = new BukkitObjectInputStream(stream);

            final ItemStack item = (ItemStack) data.readObject();

            data.close();

            return item;
        }catch (final Exception exception){
            throw new RuntimeException("Could not deserialize ItemStack", exception);
        }
    }
}
