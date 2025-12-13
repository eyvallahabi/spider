package io.github.shiryu.spider.api.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public abstract class SpiderEvent extends Event implements Cancellable {

    private static final HandlerList handlerList;

    private boolean cancelled;

    static{
        handlerList = new HandlerList();
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
