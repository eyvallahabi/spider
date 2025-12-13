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

public class ItemStackArrayConverter implements FactoryConverter<ItemStack[]> {

    @Override
    public @NotNull String deserialize(@NonNull ItemStack[] input) {
        try{
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream output = new BukkitObjectOutputStream(stream);

            output.writeInt(input.length);

            for (int i = 0; i < input.length; i++){
                output.writeObject(input[i]);
            }

            output.close();

            return Base64.getEncoder().encodeToString(stream.toByteArray());
        }catch (final Exception exception){
            throw new RuntimeException("Could not deserialize ItemStack[] to Base64", exception);
        }
    }

    @Override
    public ItemStack[] serialize(@NotNull String input) {
        try{
            final ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(input));

            final BukkitObjectInputStream data = new BukkitObjectInputStream(stream);

            final int size = data.readInt();
            final ItemStack[] items = new ItemStack[size];

            for (int i = 0; i < size; i++){
                items[i] = (ItemStack) data.readObject();
            }

            data.close();

            return items;
        }catch (final Exception exception){
            throw new RuntimeException("Could not serialize Base64 to ItemStack[]", exception);
        }
    }
}
