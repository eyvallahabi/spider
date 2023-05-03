package io.github.shiryu.spider.integration.economy.impl;

import io.github.shiryu.spider.integration.Integration;
import io.github.shiryu.spider.integration.economy.EconomyIntegration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Integration(id = "Vault")
public class VaultEconomy implements EconomyIntegration {

    private Economy economy;

    public VaultEconomy(){
        this.register();
    }

    public void register(){
        final RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (rsp == null)
            return;

        this.economy = rsp.getProvider();
    }

    @Override
    public void deposit(@NotNull UUID uuid, double money) {
        if (this.get(uuid).isEmpty())
            return;

        this.economy.depositPlayer(this.get(uuid).get(), money);
    }

    @Override
    public void withdraw(@NotNull UUID uuid, double money) {
        if (this.get(uuid).isEmpty())
            return;

        this.economy.withdrawPlayer(this.get(uuid).get(), money);
    }

    @Override
    public boolean has(@NotNull UUID uuid, double money) {
        if (this.get(uuid).isEmpty())
            return false;

        return this.economy.has(this.get(uuid).get(), money);
    }

    @Override
    public boolean exists(@NotNull UUID uuid) {
        if (this.get(uuid).isEmpty())
            return false;

        return this.economy.hasAccount(this.get(uuid).get());
    }

    private Optional<OfflinePlayer> get(@NotNull final UUID uuid){
        return Optional.ofNullable(
                Bukkit.getOfflinePlayer(uuid)
        );
    }
}
