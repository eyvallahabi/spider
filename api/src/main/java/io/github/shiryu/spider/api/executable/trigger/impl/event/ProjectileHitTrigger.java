package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.Parse;
import io.github.shiryu.spider.api.executable.parseable.ParseContext;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;

@Parse(name = "@projectilehit", description = "Triggered when a projectile hits an entity or block")
public class ProjectileHitTrigger implements EventTrigger {

    @Override
    public void initialize(@NotNull ParseContext context) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void hit(final ProjectileHitEvent event){
        final Projectile projectile = event.getEntity();
        final Entity shooter = projectile.getShooter() instanceof Entity ? (Entity) projectile.getShooter() : null;

        if (shooter == null)
            return;

        if (projectile instanceof Arrow)
            return;

        ExecutionContextBuilder builder = ExecutionContextBuilder.newBuilder()
                .caster(shooter)
                .location(shooter.getLocation())
                .world(shooter.getWorld())
                .event(event)
                .entity("projectile", projectile)
                .string("projectileType", ProjectileType.from(projectile).name());

        if (event.getHitBlock() != null)
            builder = builder.block("hitBlock", event.getHitBlock())
                    .trigger(event.getHitBlock())
                    .string("hitType", "BLOCK");

        if (event.getHitEntity() != null)
            builder = builder.entity("hitEntity", event.getHitEntity())
                    .trigger(event.getHitEntity())
                    .string("hitType", "ENTITY");

        this.call(builder.build());
    }

    public enum ProjectileType{
        ARROW,
        EGG,
        ENDER_PEARL,
        FIREBALL,
        FISHING_HOOK,
        LINGERING_POTION,
        POTION,
        SHULKER_BULLET,
        SNOWBALL,
        SPECTRAL_ARROW,
        TRIDENT,
        WITHER_SKULL;

        @NotNull
        public static ProjectileType from(@NotNull final Projectile projectile){
            return switch (projectile.getType()){
                case ARROW -> ARROW;
                case EGG -> EGG;
                case ENDER_PEARL -> ENDER_PEARL;
                case FIREBALL, SMALL_FIREBALL -> FIREBALL;
                case LINGERING_POTION -> LINGERING_POTION;
                case SPLASH_POTION -> POTION;
                case SHULKER_BULLET -> SHULKER_BULLET;
                case SNOWBALL -> SNOWBALL;
                case SPECTRAL_ARROW -> SPECTRAL_ARROW;
                case TRIDENT -> TRIDENT;
                case WITHER_SKULL -> WITHER_SKULL;
                default -> throw new IllegalArgumentException("Unsupported projectile type: " + projectile.getType());
            };
        }
    }
}
