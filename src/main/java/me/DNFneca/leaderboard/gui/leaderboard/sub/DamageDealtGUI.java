package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.gui.leaderboard.LeaderboardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DamageDealtGUI extends BaseBoardGUI {
    public DamageDealtGUI(String title, List<ItemStack> items) {
        super(title, 27, items);
    }

    public DamageDealtGUI(String title) {
        super(title, 27);

        setItem(11, ItemUtils.makeGUIItemOfType(Material.DIAMOND_SWORD, "Total Damage Dealt"));

        setItem(13, ItemUtils.makeGUIItemOfType(Material.GOLDEN_HELMET, "Total Damage Resisted"));

        setItem(15, ItemUtils.makeGUIItemOfType(Material.ENCHANTED_GOLDEN_APPLE, "Total Damage Absorbed"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Dealt")) {
            getBoard().setStatistic(Statistic.DAMAGE_DEALT);
            getBoard().getMainRow().setText(Component.text("Total Damage Dealt When Attacking").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Resisted")) {
            getBoard().setStatistic(Statistic.DAMAGE_RESISTED);
            getBoard().getMainRow().setText(Component.text("Total Damage Resisted When Attacking").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Damage Absorbed")) {
            getBoard().setStatistic(Statistic.DAMAGE_ABSORBED);
            getBoard().getMainRow().setText(Component.text("Total Damage Absorbed When Attacking").color(TextColor.fromHexString("#00ffff")));
            getBoardGUI().openForPlayer(player);
        }
    }
}
