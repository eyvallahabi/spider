package io.github.shiryu.spider.api.executable.variable.impl;

import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.World;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class WorldVariable implements Variable<World> {

    private final String name;

    private World value;
}
