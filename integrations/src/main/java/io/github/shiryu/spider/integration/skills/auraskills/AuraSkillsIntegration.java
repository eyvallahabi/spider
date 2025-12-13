package io.github.shiryu.spider.integration.skills.auraskills;

import io.github.shiryu.spider.api.integration.Integration;
import io.github.shiryu.spider.api.registry.Registries;
import io.github.shiryu.spider.integration.skills.auraskills.action.AuraAddSkillXpAction;
import io.github.shiryu.spider.integration.skills.auraskills.action.AuraConsumeManaAction;
import io.github.shiryu.spider.integration.skills.auraskills.action.AuraRemoveSkillXpAction;
import io.github.shiryu.spider.integration.skills.auraskills.requirement.AuraManaRequirement;
import io.github.shiryu.spider.integration.skills.auraskills.requirement.AuraSkillLevelRequirement;
import io.github.shiryu.spider.integration.skills.auraskills.requirement.AuraSkillXpRequirement;
import org.jetbrains.annotations.NotNull;

public class AuraSkillsIntegration implements Integration {

    @Override
    public void enable() {
        Registries.ACTION.register(
                AuraAddSkillXpAction.class,
                AuraRemoveSkillXpAction.class,
                AuraConsumeManaAction.class
        );

        Registries.REQUIREMENT.register(
                AuraManaRequirement.class,
                AuraSkillLevelRequirement.class,
                AuraSkillXpRequirement.class
        );
    }

    @Override
    public @NotNull String getPlugin() {
        return "AuraSkills";
    }
}
