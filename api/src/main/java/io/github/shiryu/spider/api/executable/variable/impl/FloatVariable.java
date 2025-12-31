package io.github.shiryu.spider.api.executable.variable.impl;

import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class FloatVariable implements Variable<Float> {

    private final String name;

    private Float value;
}
