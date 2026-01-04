package io.github.shiryu.spider.api.executable.parseable;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.ParsedArgument;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ParseArgument {

    private final String name;
    private final String value;

    private final List<ParseArgument> nested;

    private boolean optional = false;

    public ParseArgument(@NotNull final String name, @NotNull final String value){
        this(name, value, Lists.newArrayList());
    }
}
