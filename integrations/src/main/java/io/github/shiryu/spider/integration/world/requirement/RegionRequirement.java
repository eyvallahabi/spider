package io.github.shiryu.spider.integration.world.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.world.WorldGuardIntegration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "region", controls = Player.class)
public class RegionRequirement implements Requirement<Player> {

    private final String[] region;

    public RegionRequirement(@NotNull final String... args){
        this.region = args;
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        return Registries.INTEGRATION.get(WorldGuardIntegration.class)
                .isInRegions(controlled, this.region);
    }
}
