package io.github.shiryu.spider.integration.skills.auraskills.action;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ActionInfo(name = "aura_skills_remove_skill_xp")
public class AuraRemoveSkillXpAction implements Action {

    private final Skills type;
    private final double amount;

    public AuraRemoveSkillXpAction(@NotNull final String... args){
        this.type = Skills.valueOf(args[0].toUpperCase());
        this.amount = Double.parseDouble(args[1]);
    }

    @Override
    public void execute(@NotNull Player player) {
        final AuraSkillsApi api = AuraSkillsApi.get();

        final var user = api.getUserManager().getUser(player.getUniqueId());

        if (user == null)
            return;

        final double current = user.getSkillXp(this.type);

        if (current - this.amount < 0){
            user.setSkillXp(this.type, 0);
            return;
        }else{
            user.setSkillXp(this.type, current - this.amount);
        }
    }
}
