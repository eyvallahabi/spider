package io.github.shiryu.spider.api.control.controller.ext;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Action {

    void execute(@NotNull final Player executor);

}
