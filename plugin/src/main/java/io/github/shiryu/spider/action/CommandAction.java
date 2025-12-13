package io.github.shiryu.spider.action;

import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ActionInfo(name = "command")
public class CommandAction implements Action {

    private final String command;

    public CommandAction(@NotNull final String... args){
        this.command = String.join(" ", args);
    }

    @Override
    public void execute(@NotNull Player player) {
        Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                this.command.replaceAll("%player%", player.getName())
        );
    }
}
