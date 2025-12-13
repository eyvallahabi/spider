package io.github.shiryu.spider.requirement.events.enchant;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.Map;

@RequirementInfo(name = "enchantment_level_at_least", controls = EnchantItemEvent.class)
public class EnchantmentLevelAtLeastRequirement implements Requirement<EnchantItemEvent> {

    private final int level;

    public EnchantmentLevelAtLeastRequirement(final String... args) {
        this.level = Integer.parseInt(args[0]);
    }

    @Override
    public boolean control(final EnchantItemEvent event) {
        boolean result = true;

        for (final Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()){
            if (entry.getValue() < level) {
                result = false;
                break;
            }
        }

        return result;
    }
}
