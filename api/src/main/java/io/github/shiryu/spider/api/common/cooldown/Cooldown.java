package io.github.shiryu.spider.api.common.cooldown;

import io.github.shiryu.spider.api.common.TimeUtil;
import org.jetbrains.annotations.NotNull;

public interface Cooldown<Player> {

    default void put(@NotNull final Player player, @NotNull final String id, @NotNull final String amount){
        this.put(
                player,
                id,
                TimeUtil.handleParseTime(amount)
        );
    }

    void put(@NotNull final Player player, @NotNull final String id, final long amount);

    boolean active(@NotNull final Player player, @NotNull final String id);
}
