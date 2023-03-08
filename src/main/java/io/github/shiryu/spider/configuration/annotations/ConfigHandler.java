package io.github.shiryu.spider.configuration.annotations;

import org.jetbrains.annotations.NotNull;

public @interface ConfigHandler {

    @NotNull
    String name();

    @NotNull
    FileType type() default FileType.YAML;

    enum FileType{
        YAML,
        JSON,
        TOML
    }
}
