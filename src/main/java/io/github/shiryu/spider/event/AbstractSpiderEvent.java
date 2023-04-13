package io.github.shiryu.spider.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public abstract class AbstractSpiderEvent extends Event implements Cancellable {

    static{
        handlerList = new HandlerList();
    }

    private static HandlerList handlerList;

    private boolean cancelled;

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
