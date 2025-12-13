package io.github.shiryu.spider.integration.economy.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.economy.EconomyIntegration;
import io.github.shiryu.spider.integration.economy.EconomyProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@RequirementInfo(name = "money", controls = Player.class)
public class MoneyRequirement implements Requirement<Player> {

    private final double money;

    public MoneyRequirement(final String... args){
        this.money = Double.parseDouble(args[0]);
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        final EconomyProvider provider = Registries.INTEGRATION.get(EconomyIntegration.class).create();

        if (provider == null)
            return false;

        return provider.getBalance(controlled.getUniqueId()) >= this.money;
    }


}

