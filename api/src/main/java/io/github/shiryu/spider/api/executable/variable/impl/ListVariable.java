package io.github.shiryu.spider.api.executable.variable.impl;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ListVariable<T> implements Variable<List<T>> {

    private final String name;

    private List<T> value = Lists.newArrayList();
}
