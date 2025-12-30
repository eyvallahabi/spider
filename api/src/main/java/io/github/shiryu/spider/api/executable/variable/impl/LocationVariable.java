package io.github.shiryu.spider.api.executable.variable.impl;

import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LocationVariable implements Variable<Location> {

    private final String name;

    private Location value;

}
