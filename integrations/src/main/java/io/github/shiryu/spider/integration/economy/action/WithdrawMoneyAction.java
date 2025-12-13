package io.github.shiryu.spider.integration.economy.action;

import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.economy.EconomyIntegration;
import io.github.shiryu.spider.integration.economy.EconomyProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ActionInfo(name = "withdraw_money")
public class WithdrawMoneyAction implements Action {

    private final double amount;

    public WithdrawMoneyAction(@NotNull final String... args){
        this.amount = Double.parseDouble(args[0]);
    }

    @Override
    public void execute(@NotNull Player player) {
        final EconomyProvider provider = Registries.INTEGRATION.get(EconomyIntegration.class).create();

        if (provider == null)
            return;

        provider.withdraw(player.getUniqueId(), this.amount);
    }
}
