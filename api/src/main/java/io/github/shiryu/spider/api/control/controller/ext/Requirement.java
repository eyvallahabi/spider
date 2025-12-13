package io.github.shiryu.spider.api.control.controller.ext;

import org.jetbrains.annotations.NotNull;

public interface Requirement<T> {

    boolean control(@NotNull final T controlled);

}
