package io.github.shiryu.spider.integration.skills.auraskills.requirement;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.github.shiryu.spider.api.control.controller.annotation.RequirementInfo;
import io.github.shiryu.spider.api.control.controller.ext.Requirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequirementInfo(name = "aura_skills_has_mana", controls = Player.class)
public class AuraManaRequirement implements Requirement<Player> {

    private final double mana;

    public AuraManaRequirement(@NotNull final String... args){
        this.mana = Double.parseDouble(args[0]);
    }

    @Override
    public boolean control(@NotNull Player controlled) {
        final AuraSkillsApi skills = AuraSkillsApi.get();

        final SkillsUser user = skills.getUser(controlled.getUniqueId());

        if (user == null)
            return false;

        return user.getMana() >= this.mana;
    }

}
