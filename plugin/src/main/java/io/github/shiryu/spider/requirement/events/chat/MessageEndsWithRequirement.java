package io.github.shiryu.spider.requirement.events.chat;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequirementInfo(name = "message_ends_with", controls = AsyncPlayerChatEvent.class)
public class MessageEndsWithRequirement implements Requirement<AsyncPlayerChatEvent> {

    private final String suffix;

    public MessageEndsWithRequirement(final String... args) {
        this.suffix = String.join(" ", args);
    }

    @Override
    public boolean control(final AsyncPlayerChatEvent controlled) {
        return controlled.getMessage().endsWith(suffix);
    }
}
