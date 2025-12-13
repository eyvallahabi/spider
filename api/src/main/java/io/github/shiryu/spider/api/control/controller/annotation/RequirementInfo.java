package io.github.shiryu.spider.api.control.controller.annotation;


import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirementInfo {

    @NotNull
    String name();

    @NotNull
    Class<?> controls();

}
