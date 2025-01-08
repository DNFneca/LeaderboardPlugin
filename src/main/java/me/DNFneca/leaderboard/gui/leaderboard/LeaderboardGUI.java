package me.DNFneca.leaderboard.gui.leaderboard;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.gui.leaderboard.sub.LeaderboardScoresGUI;
import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LeaderboardGUI extends BaseBoardGUI {
    Board board;

    public LeaderboardGUI(String title, List<ItemStack> items) {
        super(title, 54, items);
    }

    public LeaderboardGUI(String title, Board board) {
        super(title, 54);
        this.board = board;

        setItem(10, ItemUtils.makeGUIItemOfType(Material.NAME_TAG, "Leaderboard Name (Optional)"));

        setItem(13, ItemUtils.makeGUIItemOfType(Material.REPEATING_COMMAND_BLOCK, "Leaderboard Score"));

        setItem(16, ItemUtils.makeGUIItemOfType(Material.BLUE_WOOL, "Leaderboard Color"));

        setItem(29, ItemUtils.makeGUIItemOfType(Material.PAPER, "Leaderboard Blacklist"));

        setItem(33, ItemUtils.makeGUIItemOfType(Material.STICK, "Leaderboard Size"));

        setItem(49, ItemUtils.makeGUIItemOfType(Material.BARRIER, "Close"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Close")) {
            this.closeGUI();
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Leaderboard Name (Optional)")) {

        } else if ( ItemUtils.isGUIItemWithSpecificName(itemStack, "Leaderboard Score")) {
            new LeaderboardScoresGUI("Leaderboard Scores", player, board).setParentGUI(this).openForPlayer(player);
        }
    }
}

