package io.github.shiryu.spider.requirement.events.chat;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "message_starts_with", controls = AsyncPlayerChatEvent.class)
public class MessageStartsWithRequirement implements Requirement<AsyncPlayerChatEvent> {

    private final String prefix;

    public MessageStartsWithRequirement(final String... args) {
        this.prefix = String.join(" ", args);
    }

    @Override
    public boolean control(@NotNull AsyncPlayerChatEvent controlled) {
        return controlled.getMessage().startsWith(prefix);
    }
}
