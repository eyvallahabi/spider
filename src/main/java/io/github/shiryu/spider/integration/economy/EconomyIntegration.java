package io.github.shiryu.spider.integration.economy;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface EconomyIntegration {

    void deposit(@NotNull final UUID uuid, final double money);

    void withdraw(@NotNull final UUID uuid, final double money);

    boolean has(@NotNull final UUID uuid, final double money);

    boolean exists(@NotNull final UUID uuid);
}
