package io.github.shiryu.spider.integration.skills.auraskills.requirement;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "aura_skills_skill_level", controls = Player.class)
public class AuraSkillLevelRequirement implements Requirement<Player> {

    private final Skills type;
    private final int level;

    public AuraSkillLevelRequirement(@NotNull final String... args){
        this.type = Skills.valueOf(args[0].toUpperCase());
        this.level = Integer.parseInt(args[1]);
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        final AuraSkillsApi api = AuraSkillsApi.get();

        final SkillsUser user = api.getUser(controlled.getUniqueId());

        if (user == null)
            return false;

        return user.getSkillLevel(this.type) >= this.level;
    }
}
