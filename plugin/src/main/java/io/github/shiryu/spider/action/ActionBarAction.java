package io.github.shiryu.spider.action;

import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.util.Colored;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ActionInfo(name = "action_bar")
public class ActionBarAction implements Action {

    private final String message;

    public ActionBarAction(@NotNull final String... args){
        this.message = String.join(" ", args);
    }

    @Override
    public void execute(@NotNull Player player) {
        player.sendActionBar(Colored.convert(this.message));
    }
}
