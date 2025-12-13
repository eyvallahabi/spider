package io.github.shiryu.spider.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class SpiderPlayerEvent extends PlayerEvent {

    private static final HandlerList handlerList;

    static{
        handlerList = new HandlerList();
    }

    private boolean cancelled;

    protected SpiderPlayerEvent(@NotNull Player player) {
        super(player);
    }

    protected SpiderPlayerEvent(@NotNull Player player, boolean async) {
        super(player, async);
    }

    @NotNull
    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
