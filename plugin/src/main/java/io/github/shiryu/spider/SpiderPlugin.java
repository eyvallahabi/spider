package io.github.shiryu.spider;

import io.github.shiryu.spider.api.config.Configs;
import io.github.shiryu.spider.api.factory.Factories;
import io.github.shiryu.spider.config.ItemStackSerializer;
import io.github.shiryu.spider.config.LocationSerializer;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.economy.EconomyIntegration;
import io.github.shiryu.spider.integration.enchantment.ae.AdvancedEnchantmentsIntegration;
import io.github.shiryu.spider.integration.skills.auraskills.AuraSkillsIntegration;
import io.github.shiryu.spider.integration.world.WorldGuardIntegration;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SpiderPlugin extends JavaPlugin {

    @Getter
    private static SpiderPlugin plugin;

    @Override
    public void onEnable(){
        plugin = this;

        Factories.registerAll();

        registerConfigSerializers();
        registerIntegrations();
    }

    private void registerConfigSerializers(){
        Configs.register(Location.class, new LocationSerializer());
        Configs.register(ItemStack.class, new ItemStackSerializer());
    }

    private void registerIntegrations(){
        Registries.INTEGRATION.register(EconomyIntegration.class);

        //WorldGuard
        Registries.INTEGRATION.register("WorldGuard", WorldGuardIntegration.class);

        //Skills
        Registries.INTEGRATION.register("AuraSkills", AuraSkillsIntegration.class);

        //Enchantments
        Registries.INTEGRATION.register("AdvancedEnchantments", AdvancedEnchantmentsIntegration.class);
    }

}
