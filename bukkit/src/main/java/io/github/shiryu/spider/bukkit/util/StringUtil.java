package io.github.shiryu.spider.bukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class StringUtil {

    @NotNull
    public String upperCaseWords(@NotNull final String sentence) {
        final String[] words = sentence.replace("_", " ").replaceAll("\\s+", " ").trim().split(" ");
        String newSentence = "";
        for (String word : words) {
            for (int i = 0; i < word.length(); i++)
                newSentence = newSentence + ((i == 0) ? word.substring(i, i + 1).toUpperCase() : ((i != word.length() - 1) ? word.substring(i, i + 1).toLowerCase() : (word.substring(i, i + 1).toLowerCase().toLowerCase() + " ")));
        }
        return newSentence.trim();
    }

    @NotNull
    public String vanillaEnchantment(@NotNull final Enchantment enchantment){
        switch (enchantment.getName()){
            case "ARROW_DAMAGE":
                return "Power";
            case "ARROW_FIRE":
                return "Flame";
            case "ARROW_INFINITE":
                return "Infinity";
            case "ARROW_KNOCKBACK":
                return "Punch";
            case "BINDING_CURSE":
                return "Curse of Binding";
            case "DAMAGE_ALL":
                return "Sharpness";
            case "DAMAGE_ARTHROPODS":
                return "Bane of Arthropods";
            case "DAMAGE_UNDEAD":
                return "Smite";
            case "DIG_SPEED":
                return "Efficiency";
            case "DURABILITY":
                return "Unbreaking";
            case "LOOT_BONUS_BLOCKS":
                return "Fortune";
            case "LOOT_BONUS_MOBS":
                return "Looting";
            case "LUCK":
                return "Luck of the Sea";
            case "OXYGEN":
                return "Respiration";
            case "PROTECTION_ENVIRONMENTAL":
                return "Protection";
            case "PROTECTION_EXPLOSIONS":
                return "Blast Protection";
            case "PROTECTION_FALL":
                return "Feather Falling";
            case "PROTECTION_FIRE":
                return "Fire Protection";
            case "PROTECTION_PROJECTILE":
                return "Projectile Protection";
            case "VANISHING_CURSE":
                return "Curse of Vanishing";
            case "WATER_WORKER":
                return "Aqua Affinity";
        }

        return upperCaseWords(enchantment.getName().replace('_', ' '));
    }

}
