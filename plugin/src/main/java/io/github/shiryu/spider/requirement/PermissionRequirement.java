package io.github.shiryu.spider.requirement;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
@RequirementInfo(name = "permission", controls = Player.class)
public class PermissionRequirement implements Requirement<Player> {

    private final String permission;

    public PermissionRequirement(@NotNull final String... args){
        this.permission = args[0];
    }

    @Override
    public boolean control(@NotNull Player player) {
        return player.hasPermission(permission);
    }

}
