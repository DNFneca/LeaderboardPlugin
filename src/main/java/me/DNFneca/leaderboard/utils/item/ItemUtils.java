package me.DNFneca.leaderboard.utils.item;

import me.DNFneca.leaderboard.Leaderboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static boolean isItem(Material material) {
        return material.isItem() && material != Material.AIR && !material.isBlock();
    }

    public static boolean isBlock(Material material) {
        return material.isBlock() && material != Material.AIR;
    }

    public static boolean isGUIItemWithSpecificName(ItemStack item, String name) {
        if(item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.displayName().equals(Component.text(name).color(TextColor.fromHexString("#ffffff")).decoration(TextDecoration.ITALIC, false));
    }

    public static ItemStack makeItemGUIItem(ItemStack item, String name) {
        if(item == null || name.isEmpty() || name.isBlank() || item.getItemMeta() == null) return null;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name).color(TextColor.fromHexString("#ffffff")).decoration(TextDecoration.ITALIC, false));
        meta.addItemFlags(ItemFlag.values());
        if (!meta.hasAttributeModifiers()) {
            meta.addAttributeModifier(Attribute.GENERIC_SCALE, new AttributeModifier(
                    new NamespacedKey(Leaderboard.getInstance(), "temp"),
                    1,
                    AttributeModifier.Operation.ADD_NUMBER));
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack makeGUIItemOfType(Material material, String name) {
        return makeItemGUIItem(new ItemStack(material), name);
    }

    public static ItemStack makeGUIItemOfType(Material material) {
        return makeItemGUIItem(new ItemStack(material), getFriendlyName(material));
    }

    public static ItemStack makeGUIItemsOfType(Material material, String name) {
        return makeItemGUIItem(new ItemStack(material), name);
    }

    public static String getFriendlyName(Material material) {
        if (material == null) return null;
        // Split the enum name by underscores, capitalize each word, and join them
        String[] words = material.name().toLowerCase().split("_");
        StringBuilder friendlyName = new StringBuilder();
        for (String word : words) {
            friendlyName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return friendlyName.toString().trim(); // Remove trailing space
    }

    public static boolean areGUIItemsEqual(ItemStack item1, ItemStack item2) {
        if(isGUIItemWithSpecificName(item1, PlainTextComponentSerializer.plainText().serialize(item1.displayName())) && isGUIItemWithSpecificName(item2, PlainTextComponentSerializer.plainText().serialize(item2.displayName()))) {
            Component componentOfItem1 = item1.displayName();
            Component componentOfItem2 = item2.displayName();
            return componentOfItem2.equals(componentOfItem1);
        }
        return false;
    }
}
