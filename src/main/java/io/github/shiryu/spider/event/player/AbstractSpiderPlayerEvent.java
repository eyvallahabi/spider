package io.github.shiryu.spider.event.player;

import io.github.shiryu.spider.event.AbstractSpiderEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public abstract class AbstractSpiderPlayerEvent extends AbstractSpiderEvent {

    private final Player player;
}
