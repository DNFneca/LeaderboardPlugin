package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DamageTakenGUI extends BaseBoardGUI {
    public DamageTakenGUI(String title, List<ItemStack> items) {
        super(title, 27, items);
    }

    public DamageTakenGUI(String title) {
        super(title, 27);

        setItem(10, ItemUtils.makeGUIItemOfType(Material.GOLDEN_HELMET, "Total Damage Resisted"));

        setItem(12, ItemUtils.makeGUIItemOfType(Material.ENCHANTED_GOLDEN_APPLE, "Total Damage Absorbed"));

        setItem(14, ItemUtils.makeGUIItemOfType(Material.GOLDEN_CARROT, "Total Damage Taken"));

        setItem(16, ItemUtils.makeGUIItemOfType(Material.SHIELD, "Total Damage Blocked by Shield"));

    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Resisted")) {
            getBoard().setStatistic(Statistic.DAMAGE_RESISTED);
            getBoard().getMainRow().setText(Component.text("Total Damage Resisted When Attacked").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Absorbed")) {
            getBoard().setStatistic(Statistic.DAMAGE_ABSORBED);
            getBoard().getMainRow().setText(Component.text("Total Damage Absorbed When Attacked").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Taken")) {
            getBoard().setStatistic(Statistic.DAMAGE_TAKEN);
            getBoard().getMainRow().setText(Component.text("Total Damage Taken When Attacked").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Blocked by Shield")) {
            getBoard().setStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD);
            getBoard().getMainRow().setText(Component.text("Total Damage Blocked by Shield When Attacked").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        }
    }
}
