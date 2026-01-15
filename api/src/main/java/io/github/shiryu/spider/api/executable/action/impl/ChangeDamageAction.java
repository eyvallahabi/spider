package io.github.shiryu.spider.api.executable.action.impl;

import io.github.shiryu.spider.api.executable.action.Action;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

@Parse(name = "change_damage", description = "Changes the damage of an attack.")
public class ChangeDamageAction implements Action {

    private int percent;

    @Override
    public void initialize(@NotNull ParseContext context) {
        this.percent = Integer.parseInt(context.action().getOrDefault("percent", "0"));
    }

    @Override
    public void execute(@NotNull ExecutionContext context) {
        final Event event = context.getEvent();

        if (event instanceof EntityDamageByEntityEvent damage)
            damage.setDamage(this.calculateModifiedDamage(damage.getDamage()));
        else if (event instanceof EntityDamageEvent damage)
            damage.setDamage(this.calculateModifiedDamage(damage.getDamage()));
    }
    
    private double calculateModifiedDamage(final double original){
        return original + (original * percent / 100.0);
    }
}
