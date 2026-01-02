package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

@Parse(name = "@playerkill", description = "Triggered when a player is killed by another entity")
public class PlayerKillTrigger implements EventTrigger {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @EventHandler
    public void kill(final PlayerDeathEvent event){
        final Player player = event.getEntity();

        final EntityDamageEvent lastDamageCause = player.getLastDamageCause();

        if (lastDamageCause == null)
            return;

        if (!(lastDamageCause instanceof EntityDamageByEntityEvent damage))
            return;

        Entity killer = damage.getDamager();

        if (killer instanceof Projectile projectile)
            killer = (Entity) projectile.getShooter();

        if (killer == null)
            return;

        call(
                ExecutionContextBuilder.newBuilder()
                        .caster(killer)
                        .trigger(player)
                        .player("target", player)
                        .event(event)
                        .damageSource("source", damage.getDamageSource())
                        .location(killer.getLocation())
                        .world(killer.getWorld())
                        .build()
        );
    }

}
