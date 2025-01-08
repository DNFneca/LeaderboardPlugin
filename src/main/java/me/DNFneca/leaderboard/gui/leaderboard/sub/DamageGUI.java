package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DamageGUI extends BaseBoardGUI {
    public DamageGUI(String title, List<ItemStack> items) {
        super(title, 27, items);
    }

    public DamageGUI(String title) {
        super(title, 27);

        setItem(11, ItemUtils.makeGUIItemOfType(Material.DIAMOND_SWORD, "Damage Dealt Category"));

        setItem(15, ItemUtils.makeGUIItemOfType(Material.SHIELD, "Damage Taken Category"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Damage Dealt Category")) {
            new DamageDealtGUI("Damage Dealt Category").setParentGUI(this).openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Damage Taken Category")) {
            new DamageTakenGUI("Damage Taken Category").setParentGUI(this).openForPlayer(player);
        }
    }
}
