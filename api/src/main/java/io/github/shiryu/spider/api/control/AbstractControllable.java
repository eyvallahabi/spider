package io.github.shiryu.spider.api.control;

import io.github.shiryu.spider.api.control.controller.ext.Action;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class AbstractControllable implements Controllable {

    private final List<Requirement> requirements;
    private final List<Action> actions;

}
