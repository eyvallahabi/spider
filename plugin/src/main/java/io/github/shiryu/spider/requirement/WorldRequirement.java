package io.github.shiryu.spider.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@RequiredArgsConstructor
@RequirementInfo(name = "world", controls = Player.class)
public class WorldRequirement implements Requirement<Player> {

    private final List<String> world;

    public WorldRequirement(@NotNull final String... args){
        this.world = List.of(args);
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        return this.world.contains(controlled.getWorld().getName());
    }
}
