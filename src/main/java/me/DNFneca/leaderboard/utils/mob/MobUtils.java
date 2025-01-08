package me.DNFneca.leaderboard.utils.mob;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class MobUtils {
    public static EntityType getEntityType(final String name) {
        return EntityType.fromName(name.toUpperCase().replace(" ", "_"));
    }

    public static String getFriendlyName(EntityType material) {
        if (material == null) return null;
        // Split the enum name by underscores, capitalize each word, and join them
        String[] words = material.name().toLowerCase().split("_");
        StringBuilder friendlyName = new StringBuilder();
        for (String word : words) {
            friendlyName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return friendlyName.toString().trim(); // Remove trailing space
    }
}
