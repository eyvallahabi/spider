package io.github.shiryu.spider.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public abstract class AbstractSpiderEvent extends Event implements Cancellable {

    protected static HandlerList handlers;

    static{
        handlers = new HandlerList();
    }

    private boolean cancelled;

    @NotNull
    public static HandlerList getHandlerList(){
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
