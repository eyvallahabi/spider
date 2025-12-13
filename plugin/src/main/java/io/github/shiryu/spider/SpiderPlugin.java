package io.github.shiryu.spider;

import io.github.shiryu.spider.action.ActionBarAction;
import io.github.shiryu.spider.action.CommandAction;
import io.github.shiryu.spider.action.MessageAction;
import io.github.shiryu.spider.action.TitleAction;
import io.github.shiryu.spider.api.config.Configs;
import io.github.shiryu.spider.config.ItemStackSerializer;
import io.github.shiryu.spider.config.LocationSerializer;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.economy.EconomyIntegration;
import io.github.shiryu.spider.integration.skills.auraskills.AuraSkillsIntegration;
import io.github.shiryu.spider.integration.world.WorldGuardIntegration;
import io.github.shiryu.spider.requirement.*;
import io.github.shiryu.spider.requirement.events.breeding.ChildTypeRequirement;
import io.github.shiryu.spider.requirement.events.chat.MessageContainsRequirement;
import io.github.shiryu.spider.requirement.events.chat.MessageEndsWithRequirement;
import io.github.shiryu.spider.requirement.events.chat.MessageStartsWithRequirement;
import io.github.shiryu.spider.requirement.events.consume.ConsumedItemTypeRequirement;
import io.github.shiryu.spider.requirement.events.crafting.CraftedItemTypeRequirement;
import io.github.shiryu.spider.requirement.events.damage.*;
import io.github.shiryu.spider.requirement.events.death.KillerIsPlayerRequirement;
import io.github.shiryu.spider.requirement.events.death.VictimIsAnimalRequirement;
import io.github.shiryu.spider.requirement.events.death.VictimIsPlayerRequirement;
import io.github.shiryu.spider.requirement.events.death.VictimTypeRequirement;
import io.github.shiryu.spider.requirement.events.enchant.EnchantedItemTypeRequirement;
import io.github.shiryu.spider.requirement.events.enchant.EnchantmentLevelAtLeastRequirement;
import io.github.shiryu.spider.requirement.events.enchant.EnchantmentLevelRequirement;
import io.github.shiryu.spider.requirement.events.enchant.EnchantmentTypeRequirement;
import io.github.shiryu.spider.requirement.events.fishing.CaughtTypeRequirement;
import io.github.shiryu.spider.requirement.nbt.ItemHasNBTRequirement;
import io.github.shiryu.spider.requirement.nbt.ItemNBTValueRequirement;
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

        this.initialize();
    }

    public void initialize(){
        Configs.register(Location.class, new LocationSerializer());
        Configs.register(ItemStack.class, new ItemStackSerializer());

        Registries.ACTION.register(
                ActionBarAction.class,
                CommandAction.class,
                MessageAction.class,
                TitleAction.class
        );

        Registries.REQUIREMENT.register(
                BlacklistRequirement.class,
                ItemAmountRequirement.class,
                ItemEnchantmentLevelRequirement.class,
                PermissionRequirement.class,
                PlayerNameRequirement.class,
                WhitelistRequirement.class,
                WorldRequirement.class,
                ItemHasNBTRequirement.class,
                ItemNBTValueRequirement.class,
                ChildTypeRequirement.class,
                MessageContainsRequirement.class,
                MessageEndsWithRequirement.class,
                MessageStartsWithRequirement.class,
                ConsumedItemTypeRequirement.class,
                CraftedItemTypeRequirement.class,
                DamagedIsAnimalRequirement.class,
                DamagedIsMonsterRequirement.class,
                DamagedIsPlayerRequirement.class,
                DamagedNameRequirement.class,
                DamagerIsPlayerRequirement.class,
                DamagerNameRequirement.class,
                KillerIsPlayerRequirement.class,
                VictimIsAnimalRequirement.class,
                VictimIsPlayerRequirement.class,
                VictimTypeRequirement.class,
                EnchantedItemTypeRequirement.class,
                EnchantmentLevelAtLeastRequirement.class,
                EnchantmentLevelRequirement.class,
                EnchantmentTypeRequirement.class,
                CaughtTypeRequirement.class
        );

        Registries.INTEGRATION.register(EconomyIntegration.class);

        //WorldGuard
        Registries.INTEGRATION.register("WorldGuard", WorldGuardIntegration.class);

        //Skills
        Registries.INTEGRATION.register("AuraSkills", AuraSkillsIntegration.class);
    }

}
