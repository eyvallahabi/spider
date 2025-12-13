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
@RequirementInfo(name = "blacklist", controls = Player.class)
public class BlacklistRequirement implements Requirement<Player> {

    private final List<String> blacklist;

    public BlacklistRequirement(@NotNull final String... args){
        this.blacklist = List.of(args);
    }

    @Override
    public boolean control(@NotNull Player player) {
        return !this.blacklist.contains(player.getName());
    }
}
