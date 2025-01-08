package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.UUID;

public class DamageKillsAndDeathsGUI extends BaseBoardGUI {

    public DamageKillsAndDeathsGUI(String title, List<ItemStack> items) {
        super(title, 24, items);
    }

    public DamageKillsAndDeathsGUI(String title) {
        super(title, 27);

        setItem(11, ItemUtils.makeGUIItemOfType(Material.GOLDEN_SWORD, "Damage"));

        setItem(13, ItemUtils.makeGUIItemOfType(Material.DIAMOND_SWORD, "Kills"));

        setItem(15, ItemUtils.makeGUIItemOfType(Material.SOUL_SAND, "Deaths"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Damage")) {
            new DamageGUI("Damage").setParentGUI(this).openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Kills")) {
            new KillsGUI("Kills").setParentGUI(this).openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Deaths")) {
            new DeathsGUI("Deaths").setParentGUI(this).openForPlayer(player);
        }
    }
}
