package io.github.shiryu.spider.integration.skills.auraskills.action;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ActionInfo(name = "aura_skills_add_skill_xp")
public class AuraAddSkillXpAction implements Action {

    private final Skills type;
    private final double amount;

    public AuraAddSkillXpAction(@NotNull final String... args){
        this.type = Skills.valueOf(args[0].toUpperCase());
        this.amount = Double.parseDouble(args[1]);
    }

    @Override
    public void execute(@NotNull Player player) {
        final AuraSkillsApi api = AuraSkillsApi.get();

        final var user = api.getUserManager().getUser(player.getUniqueId());

        if (user == null)
            return;

        user.addSkillXp(this.type, this.amount);
    }
}
