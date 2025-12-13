package io.github.shiryu.spider.integration.placeholder;

import io.github.shiryu.spider.api.integration.Integration;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlaceholderIntegration extends Integration {

    @NotNull
    List<PlaceholderExpansion> getExpansions();

    default void register(@NotNull final String author, @NotNull final String identifier, @NotNull final String version, @NotNull final PlaceholderTranslator translator){
        getExpansions().add(new PlaceholderExpansion() {
            @Override
            public @NotNull String getIdentifier() {
                return identifier;
            }

            @Override
            public @NotNull String getAuthor() {
                return author;
            }

            @Override
            public @NotNull String getVersion() {
                return version;
            }

            @Override
            public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
                return translator.translate(player, params);
            }
        });

        Bukkit.getLogger().info("[Spider] Registering PlaceholderExpansion '" + identifier + "' by " + author);
    }

}
