package io.github.shiryu.spider.api.executable.parseable;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parse {

    @NotNull
    String name();

    @NotNull
    String[] aliases() default {};

    @NotNull
    String description() default "";
}
