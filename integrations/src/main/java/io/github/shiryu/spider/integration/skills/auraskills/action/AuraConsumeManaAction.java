package io.github.shiryu.spider.integration.skills.auraskills.action;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import io.github.shiryu.spider.api.control.controller.annotation.ActionInfo;
import io.github.shiryu.spider.api.control.controller.ext.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ActionInfo(name = "aura_skills_consume_mana")
public class AuraConsumeManaAction implements Action {

    private final double amount;

    public AuraConsumeManaAction(@NotNull final String... args){
        this.amount = Double.parseDouble(args[0]);
    }

    @Override
    public void execute(@NotNull Player player) {
        final AuraSkillsApi api = AuraSkillsApi.get();

        final var user = api.getUserManager().getUser(player.getUniqueId());

        if (user == null)
            return;

        user.consumeMana(this.amount);
    }
}
