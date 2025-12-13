package io.github.shiryu.spider.api.registry;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.integration.Integration;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class IntegrationRegistry implements io.github.shiryu.spider.api.integration.IntegrationRegistry {

    private final List<Integration> integrations = Lists.newArrayList();

    @Override
    public void register(@NotNull Class<? extends Integration> clazz) {
        try{
            final Integration integration = clazz.getDeclaredConstructor().newInstance();

            register(integration);
        } catch (final Exception e){
            Bukkit.getLogger().severe("[Spider] An error occurred while trying to register integration " + clazz.getSimpleName() + "!");
        }
    }

    @Override
    public void register(@NotNull String name, @NotNull Class<? extends Integration> clazz) {
        if (!Bukkit.getPluginManager().isPluginEnabled(name))
            return;

        if (Bukkit.getPluginManager().getPlugin(name) == null)
            return;

        register(clazz);
    }

    @Override
    public void register(@NotNull Integration integration) {
        if (this.integrations.contains(integration))
            return;

        Bukkit.getLogger().info("[Spider] Detected plugin " + integration.getPlugin() + ", enabling integration...");

        integration.enable();

        this.integrations.add(integration);
    }

    @Override
    public <T extends Integration> @NotNull T get(@NotNull Class<T> clazz) {
        return this.integrations.stream()
                .filter(integration -> integration.getClass().equals(clazz))
                .map(integration -> (T) integration)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Integration " + clazz.getSimpleName() + " not found!"));
    }

    @Override
    public boolean isRegistered(@NotNull String name) {
        return false;
    }

    @Override
    public boolean isRegistered(@NotNull Class<? extends Integration> clazz) {
        return false;
    }
}
