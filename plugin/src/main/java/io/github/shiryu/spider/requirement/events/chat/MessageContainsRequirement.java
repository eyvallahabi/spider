package io.github.shiryu.spider.requirement.events.chat;

import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequirementInfo(name = "message_contains", controls = AsyncPlayerChatEvent.class)
public class MessageContainsRequirement implements Requirement<AsyncPlayerChatEvent> {

    private final String substring;

    public MessageContainsRequirement(final String... args) {
        this.substring = String.join(" ", args);
    }

    @Override
    public boolean control(final AsyncPlayerChatEvent controlled) {
        return controlled.getMessage().contains(substring);
    }
}
