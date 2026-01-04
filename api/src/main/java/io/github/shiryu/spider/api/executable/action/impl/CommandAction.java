package io.github.shiryu.spider.api.executable.action.impl;

import io.github.shiryu.spider.api.executable.action.Action;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Parse(name = "command", description = "Executes a command as the target players or console")
public class CommandAction implements Action {

    private String command;
    private boolean console;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.command = context.action().get("command");
        this.console = Boolean.parseBoolean(context.action().getOrDefault("console", "false"));
    }

    @Override
    public void execute(@NotNull ExecutionContext context) {
        final List<Entity> targets = context.getTarget();

        if (targets == null)
            return;

        targets.stream()
                .filter(target -> target instanceof Player)
                .map(target -> (Player) target)
                .forEach(player ->{
                    if (this.console)
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command.replaceAll("%player%", player.getName()));
                    else
                        player.performCommand(this.command.replaceAll("%player%", player.getName()));
                });
    }
}
