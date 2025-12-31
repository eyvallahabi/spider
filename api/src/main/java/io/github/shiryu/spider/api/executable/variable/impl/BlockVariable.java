package io.github.shiryu.spider.api.executable.variable.impl;

import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.block.Block;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BlockVariable implements Variable<Block> {

    private final String name;

    private Block value;

}
