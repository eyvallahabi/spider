package io.github.shiryu.spider.action;

import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.util.Colored;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ActionInfo(name = "message")
public class MessageAction implements Action {

    private final String message;

    public MessageAction(@NotNull final String... args){
        this.message = String.join(" ", args);
    }

    @Override
    public void execute(@NotNull Player player) {
        player.sendMessage(Colored.convert(this.message));
    }
}
