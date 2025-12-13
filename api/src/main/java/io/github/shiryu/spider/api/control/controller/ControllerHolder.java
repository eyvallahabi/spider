package io.github.shiryu.spider.api.control.controller;

import org.jetbrains.annotations.NotNull;

public interface ControllerHolder {

    @NotNull
    Class<?> getClazz();

    @NotNull
    String getId();

}
