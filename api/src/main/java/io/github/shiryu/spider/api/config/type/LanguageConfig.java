package io.github.shiryu.spider.api.config.type;

import io.github.shiryu.spider.api.common.Language;
import io.github.shiryu.spider.api.config.LoadableConfig;
import org.jetbrains.annotations.NotNull;

public interface LanguageConfig extends LoadableConfig {

    /**
     * Get the selected language
     *
     * @return the selected language
     */
    @NotNull
    Language getSelectedLanguage();

}
