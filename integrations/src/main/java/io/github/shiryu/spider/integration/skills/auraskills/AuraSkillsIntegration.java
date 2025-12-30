package io.github.shiryu.spider.integration.skills.auraskills;

import io.github.shiryu.spider.api.integration.Integration;
import org.jetbrains.annotations.NotNull;

public class AuraSkillsIntegration implements Integration {

    @Override
    public void enable() {

    }

    @Override
    public @NotNull String getPlugin() {
        return "AuraSkills";
    }
}
