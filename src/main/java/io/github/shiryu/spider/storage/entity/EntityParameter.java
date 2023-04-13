package io.github.shiryu.spider.storage.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EntityParameter<T> {

    private final String id;
    private final Class<?> type;

    private T value;
}
