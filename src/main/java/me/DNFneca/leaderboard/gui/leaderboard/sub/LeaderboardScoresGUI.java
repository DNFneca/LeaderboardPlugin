package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

import static me.DNFneca.leaderboard.utils.component.ComponentUtils.areComponentsEqual;

public class LeaderboardScoresGUI extends BaseBoardGUI {
    Board board;
    public LeaderboardScoresGUI(String title, int size, List<ItemStack> items) {
        super(title, size, items);
    }

    public LeaderboardScoresGUI(String title, Player player, Board board) {
        super(title, 54);
        this.board = board;

        setItem(10, ItemUtils.makeGUIItemOfType(Material.GOLDEN_SWORD, "Damage, Kills & Deaths"));

        setItem(12, ItemUtils.makeGUIItemOfType(Material.GRASS_BLOCK, "Blocks"));

        setItem(14, ItemUtils.makeGUIItemOfType(Material.BLAZE_ROD, "Items"));

        setItem(16, ItemUtils.makeGUIItemOfType(Material.LEATHER_BOOTS, "Movement"));

        setItem(37, ItemUtils.makeGUIItemOfType(Material.CLOCK, "Time"));

        setItem(43, ItemUtils.makeGUIItemOfType(Material.APPLE, "Misc"));
    }

    @Override
    public BaseGUI setParentGUI(BaseGUI parentGUI) {
        setItem(size - 5, ItemUtils.makeGUIItemOfType(Material.ARROW, "Back"));
        this.parentGUI = parentGUI;
        return this;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Damage, Kills & Deaths")) {
            new DamageKillsAndDeathsGUI("Damage, Kills & Deaths").setParentGUI(this).openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Blocks")) {
            new BlocksGUI("Placed Blocks Leaderboard").setParentGUI(this).openForPlayer(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Items")) {

        }
    }
}