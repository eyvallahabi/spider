package io.github.shiryu.spider.integration.economy.impl;

import io.github.shiryu.spider.integration.economy.EconomyProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class VaultEconomyProvider implements EconomyProvider {

    private Economy economy;

    @Override
    public void enable() {
        try{
            this.economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        }catch (final Exception exception){
            throw new IllegalStateException("Failed to enable VaultEconomyProvider", exception);
        }
    }

    @Override
    public void deposit(@NotNull UUID uuid, double amount) {
        this.economy.depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
    }

    @Override
    public void withdraw(@NotNull UUID uuid, double amount) {
        this.economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount);
    }

    @Override
    public double getBalance(@NotNull UUID uuid) {
        return this.economy.getBalance(Bukkit.getOfflinePlayer(uuid));
    }

    @Override
    public boolean has(@NotNull UUID uuid, double amount) {
        return this.economy.has(Bukkit.getOfflinePlayer(uuid), amount);
    }

    @Override
    public void set(@NotNull UUID uuid, double amount) {
        this.economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), this.economy.getBalance(Bukkit.getOfflinePlayer(uuid)));
        this.economy.depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
    }

    @Override
    public void reset(@NotNull UUID uuid) {
        this.economy.withdrawPlayer(Bukkit.getOfflinePlayer(uuid), this.economy.getBalance(Bukkit.getOfflinePlayer(uuid)));
    }
}
