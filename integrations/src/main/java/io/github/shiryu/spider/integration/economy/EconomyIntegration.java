package io.github.shiryu.spider.integration.economy;

import io.github.shiryu.spider.api.integration.Integration;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;

public class EconomyIntegration implements Integration {

    private EconomyType type;

    @Override
    public void enable() {

    }

    @Override
    public boolean isEnabled() {
        return this.getType() != null;
    }

    @Nullable
    public EconomyProvider create(){
        if (this.type != null)
            return this.type.create();

        return null;
    }

    @Override
    public @NonNull String getPlugin() {
        this.type = this.getType();

        if (this.type == null)
            return "";

        return this.type.getName();
    }

    @Nullable
    public EconomyType getType(){
        return Arrays.stream(EconomyType.values())
                .filter(type -> Bukkit.getPluginManager().isPluginEnabled(type.getName()))
                .findFirst()
                .orElse(null);
    }

}
