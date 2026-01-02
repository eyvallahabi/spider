package io.github.shiryu.spider.api.executable.parseable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ParseArgument {

    private final String name;
    private final String value;

    private boolean optional = false;

}
