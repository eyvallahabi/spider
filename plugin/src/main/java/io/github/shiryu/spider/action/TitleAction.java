package io.github.shiryu.spider.action;


import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.util.Colored;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ActionInfo(name = "title")
public class TitleAction implements Action {

    private final String title;
    private final String subtitle;

    public TitleAction(final String... args){
        this.title = args.length > 0 ? args[0] : "";
        this.subtitle = args.length > 1 ? args[1] : "";
    }

    @Override
    public void execute(@NotNull final Player player) {
        player.sendTitle(Colored.convert(this.title), Colored.convert(this.subtitle));
    }
}
