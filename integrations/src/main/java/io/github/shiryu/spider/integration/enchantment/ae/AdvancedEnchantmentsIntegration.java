package io.github.shiryu.spider.integration.enchantment.ae;

import io.github.shiryu.spider.api.integration.Integration;
import io.github.shiryu.spider.integration.enchantment.ae.effects.TimberEffect;
import net.advancedplugins.ae.api.AEAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class AdvancedEnchantmentsIntegration implements Integration {

    @Override
    public @NotNull String getPlugin() {
        return "AdvancedEnchantments";
    }

    @Override
    public void enable() {
        final JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("spider");

        AEAPI.registerEffect(plugin, new TimberEffect(plugin));
    }

}
