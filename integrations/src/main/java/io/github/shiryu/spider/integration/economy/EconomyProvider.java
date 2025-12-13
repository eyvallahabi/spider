package io.github.shiryu.spider.integration.economy;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface EconomyProvider {

    void enable();

    void deposit(@NotNull final UUID uuid, final double amount);

    void withdraw(@NotNull final UUID uuid, final double amount);

    double getBalance(@NotNull final UUID uuid);

    boolean has(@NotNull final UUID uuid, final double amount);

    void set(@NotNull final UUID uuid, final double amount);

    void reset(@NotNull final UUID uuid);

}
