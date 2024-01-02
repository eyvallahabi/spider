package io.github.shiryu.spider.api.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {

    TURKISH("tr", "Türkçe"),
    ENGLISH("eng", "English"),
    GERMAN("ger", "Deutsch"),
    SPANISH("spa", "Español"),
    FRENCH("fra", "Français");

    private final String id;
    private final String display;

}
