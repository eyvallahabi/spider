package io.github.shiryu.spider.api.executable.action.impl;

import io.github.shiryu.spider.api.executable.action.Action;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAction implements Action {

    private String message;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.message = context.action().get("message");
    }

    @Override
    public void execute(@NotNull ExecutionContext context) {
        final List<Entity> targets = context.getTarget();

        if (targets == null)
            return;

        targets.stream()
                .filter(target -> target instanceof Player)
                .map(target -> (Player) target)
                .forEach(player ->
                        player.sendMessage(
                                MiniMessage.miniMessage()
                                        .deserialize(this.message, TagResolver.standard())
                        )
                );
    }
}
