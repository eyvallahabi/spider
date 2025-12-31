package io.github.shiryu.spider.api.executable.variable.impl;

import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.damage.DamageSource;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class DamageSourceVariable implements Variable<DamageSource> {

    private final String name;

    private DamageSource value;
}
