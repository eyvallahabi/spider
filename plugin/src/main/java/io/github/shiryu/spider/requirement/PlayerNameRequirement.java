package io.github.shiryu.spider.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequirementInfo(name = "player_name", controls = Player.class)
public class PlayerNameRequirement implements Requirement<Player> {

    private final List<String> names;

    public PlayerNameRequirement(@NotNull final String... args){
        this.names = List.of(args);
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        return names.contains(controlled.getName());
    }
}
