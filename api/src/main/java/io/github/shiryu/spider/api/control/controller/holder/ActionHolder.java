package io.github.shiryu.spider.api.control.controller.holder;

import io.github.shiryu.spider.api.control.controller.ControllerHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActionHolder implements ControllerHolder {

    private final Class<?> clazz;
    private final String id;

}
