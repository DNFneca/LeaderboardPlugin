package me.DNFneca.leaderboard.utils.gui;

import me.DNFneca.leaderboard.gui.leaderboard.LeaderboardGUI;
import me.DNFneca.leaderboard.gui.leaderboard.sub.LeaderboardScoresGUI;
import me.DNFneca.leaderboard.utils.board.Board;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BaseBoardGUI extends BaseGUI {
    public BaseBoardGUI(String title, int size, List<ItemStack> items) {
        super(title, size, items);
    }

    public BaseBoardGUI(String title, int size) {
        super(title, size);
    }

    public LeaderboardGUI getBoardGUI() {
        BaseGUI baseGUI = this;
        while (baseGUI.getClass() != LeaderboardGUI.class && baseGUI.getClass() != null) {
            baseGUI = baseGUI.getParentGUI();
        }
        return (LeaderboardGUI) baseGUI;
    }

    public Board getBoard() {
        BaseGUI baseGUI = this;
        while (baseGUI.getClass() != LeaderboardScoresGUI.class) {
            baseGUI = baseGUI.getParentGUI();
        }
        LeaderboardScoresGUI leaderboardScoresGUI = (LeaderboardScoresGUI) baseGUI;
        return leaderboardScoresGUI.getBoard();
    }

//    public
}
