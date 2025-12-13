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
@RequirementInfo(name = "whitelist", controls = Player.class)
public class WhitelistRequirement implements Requirement<Player> {

    private final List<String> whitelist;

    public WhitelistRequirement(@NotNull final String... args){
        this.whitelist = List.of(args);
    }

    @Override
    public boolean control(@NotNull Player player) {
        return this.whitelist.contains(player.getName());
    }
}
