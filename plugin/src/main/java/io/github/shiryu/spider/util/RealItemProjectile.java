package io.github.shiryu.spider.util;

import io.github.shiryu.spider.SpiderPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.BiConsumer;

public class RealItemProjectile {

    @NotNull
    public static RealItemProjectile create(){
        return new RealItemProjectile();
    }

    private Material material;
    private int customModelData = -1;

    private Player shooter;
    private double velocity = 1.8;
    private double rotationSpeed = 0.25;
    private boolean gravity = false;

    private boolean bounce = true;
    private double bounceDamping = 0.55;
    private int bounceCount = 0;
    private int maxBounces = 3;

    private Particle trailParticle = null;

    private int cooldownTicks = 0;

    private BiConsumer<Block, RealItemProjectile> hitBlockCallback;
    private BiConsumer<Entity, RealItemProjectile> hitEntityCallback;

    private ArmorStand stand;
    private Vector direction;

    private RealItemProjectile(){

    }

    public RealItemProjectile material(Material material) {
        this.material = material;
        return this;
    }

    public RealItemProjectile from(Player shooter) {
        this.shooter = shooter;
        return this;
    }

    public RealItemProjectile velocity(double velocity) {
        this.velocity = velocity;
        return this;
    }

    public RealItemProjectile maxBounces(int maxBounces) {
        this.maxBounces = maxBounces;
        return this;
    }

    public RealItemProjectile rotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
        return this;
    }

    public RealItemProjectile gravity(boolean gravity) {
        this.gravity = gravity;
        return this;
    }

    public RealItemProjectile bounce(boolean enabled) {
        this.bounce = enabled;
        return this;
    }

    public RealItemProjectile bounceDamping(double damping) {
        this.bounceDamping = damping;
        return this;
    }

    public RealItemProjectile particle(Particle particle) {
        this.trailParticle = particle;
        return this;
    }

    public RealItemProjectile cooldown(int ticks) {
        this.cooldownTicks = ticks;
        return this;
    }

    public RealItemProjectile customModelData(int data) {
        this.customModelData = data;
        return this;
    }

    public RealItemProjectile onHitBlock(BiConsumer<Block, RealItemProjectile> cb) {
        this.hitBlockCallback = cb;
        return this;
    }

    public RealItemProjectile onHitEntity(BiConsumer<Entity, RealItemProjectile> cb) {
        this.hitEntityCallback = cb;
        return this;
    }

    public void start() {
        if (this.shooter == null || this.material == null)
            return;

        if (this.cooldownTicks > 0){
            if (this.shooter.hasMetadata("realproj_cd"))
                return;

            this.shooter.setMetadata("realproj_cd", new FixedMetadataValue(SpiderPlugin.getPlugin(), true));

            Bukkit.getScheduler().runTaskLater(
                    SpiderPlugin.getPlugin(),
                    () -> this.shooter.removeMetadata("realproj_cd", SpiderPlugin.getPlugin()),
                    this.cooldownTicks
            );
        }

        final World world = shooter.getWorld();
        final Location location = shooter.getEyeLocation();

        this.stand = world.spawn(
                location,
                ArmorStand.class,
                as ->{
                    as.setInvisible(true);
                    as.setMarker(true);
                    as.setGravity(false);
                    as.setInvulnerable(true);

                    ItemStack itemStack = new ItemStack(material);
                    if (customModelData != -1)
                        itemStack.editMeta(meta -> meta.setCustomModelData(customModelData));

                    as.getEquipment().setItemInMainHand(itemStack);
                }
        );

        this.direction = location.getDirection().multiply(this.velocity);

        new ShootTask(this, world).runTaskTimer(SpiderPlugin.getPlugin(), 1, 1);
    }

    public void hit(@NotNull final World world){
        final RayTraceResult result = world.rayTrace(
                stand.getLocation().add(0, 0.5, 0),
                direction.normalize(),
                direction.length(),
                FluidCollisionMode.NEVER,
                true,
                0.1,
                entity -> {
                    final UUID uuid = entity.getUniqueId();

                    return !uuid.equals(shooter.getUniqueId()) && !uuid.equals(stand.getUniqueId());
                }
        );

        if (result != null) {
            if (result.getHitEntity() != null) {
                if (hitEntityCallback != null)
                    hitEntityCallback.accept(result.getHitEntity(), this);

                return;
            }

            if (result.getHitBlock() != null){
                if (this.hitBlockCallback != null)
                    this.hitBlockCallback.accept(result.getHitBlock(), this);

                if (!this.bounce)
                    return;

                final BlockFace face = result.getHitBlockFace();

                if (face == null)
                    return;

                this.bounceCount++;

                if (this.bounceCount > this.maxBounces)
                    return;

                final Vector normal = face.getDirection().normalize();

                Vector velocity = this.direction.clone().subtract(
                        normal.clone().multiply(
                                2 * this.direction.dot(normal)
                        )
                );

                velocity = velocity.multiply(this.bounceDamping);

                direction = velocity.clone();

                if (direction.length() < 0.2)
                    this.stand.remove();

            }
        }
    }

    @Getter
    @RequiredArgsConstructor
    public class ShootTask extends BukkitRunnable{

        private final RealItemProjectile projectile;
        private final World world;

        private double rot = 0;

        @Override
        public void run() {
            if (!stand.isValid()) {
                cancel();
                return;
            }

            // gravity
            if (gravity)
                direction.add(new Vector(0, -0.04, 0));

            // trail
            if (trailParticle != null)
                world.spawnParticle(trailParticle, stand.getLocation().add(0, 0.5, 0), 1, 0, 0, 0, 0);

            // movement
            stand.teleport(stand.getLocation().add(direction));

            // rotation
            rot += rotationSpeed;
            stand.setRightArmPose(new EulerAngle(rot, 0, 0));

            hit(this.world);
        }
    }
}