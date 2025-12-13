package io.github.shiryu.spider.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.WaterMob;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class EntityUtil {

    public boolean isMonster(@NotNull final Entity entity){
        if (VersionUtil.getMajorVersion() >= 12){
            return entity instanceof Monster;
        }else{
            return (entity instanceof Monster || entity instanceof WaterMob) && !(entity instanceof Animals);
        }
    }

}
