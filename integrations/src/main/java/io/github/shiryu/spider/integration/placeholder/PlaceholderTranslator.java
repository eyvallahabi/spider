package io.github.shiryu.spider.integration.placeholder;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface PlaceholderTranslator {

    @NotNull
    String translate(@NotNull final OfflinePlayer player, @NotNull final String text);

}
