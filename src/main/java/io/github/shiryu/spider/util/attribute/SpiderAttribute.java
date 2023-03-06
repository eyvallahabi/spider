package io.github.shiryu.spider.util.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum SpiderAttribute {

    GENERIC_MAX_HEALTH("generic.maxHealth", "maxHealth"),
    GENERIC_FOLLOW_RANGE("generic.followRange", "FOLLOW_RANGE"),
    GENERIC_KNOCKBACK_RESISTANCE("generic.knockbackResistance", "c"),
    GENERIC_MOVEMENT_SPEED("generic.movementSpeed", "MOVEMENT_SPEED"),
    GENERIC_FLYING_SPEED("generic.flyingSpeed"),
    GENERIC_ATTACK_DAMAGE("generic.attackDamage", "ATTACK_DAMAGE"),
    GENERIC_ATTACK_SPEED("generic.attackSpeed"),
    GENERIC_ARMOR("generic.armor"),
    GENERIC_ARMOR_TOUGHNESS("generic.armorToughness"),
    GENERIC_LUCK("generic.luck"),
    HORSE_JUMP_STRENGTH("horse.jumpStrength"),
    ZOMBIE_SPAWN_REINFORCEMENTS("zombie.spawnReinforcements");

    private final String id;

    @Setter
    private String field;

    public boolean isLegacy(){
        return this.field != null;
    }

    @NotNull
    public Double get(@NotNull final LivingEntity entity){
        if (!isLegacy()){
            final AttributeInstance instance = entity.getAttribute(this.parseAttribute());

            if (instance == null)
                return 0.0D;

            return instance.getBaseValue();
        }else{
            //TODO DO LEGACY

            return 0.0D;
        }
    }

    public void set(@NotNull final LivingEntity entity, final double value){
        if (isLegacy()){
            //TODO

            return;
        }

        entity.getAttribute(this.parseAttribute())
                .setBaseValue(value);
    }

    @Nullable
    public Attribute parseAttribute(){
        return Attribute.valueOf(this.toString());
    }
}
