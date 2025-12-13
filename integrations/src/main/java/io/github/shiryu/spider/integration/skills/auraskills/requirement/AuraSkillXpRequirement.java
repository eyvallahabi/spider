package io.github.shiryu.spider.integration.skills.auraskills.requirement;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "aura_skills_skill_xp", controls = Player.class)
public class AuraSkillXpRequirement implements Requirement<Player> {

    private final Skills type;
    private final double xp;

    public AuraSkillXpRequirement(@NotNull final String... args){
        this.type = Skills.valueOf(args[0].toUpperCase());
        this.xp = Double.parseDouble(args[1]);
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        final AuraSkillsApi api = AuraSkillsApi.get();

        final var user = api.getUser(controlled.getUniqueId());

        if (user == null)
            return false;

        return user.getSkillXp(this.type) >= this.xp;
    }
}
