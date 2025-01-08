package me.DNFneca.leaderboard.utils.gui;

import me.DNFneca.leaderboard.gui.leaderboard.LeaderboardGUI;
import me.DNFneca.leaderboard.utils.board.Board;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUIManager implements Listener {
    public static List<BaseGUI> GUIs = new ArrayList<BaseGUI>();


    @EventHandler
    public void onPlayerInteractWithInventory(InventoryClickEvent event) {
        // Check if the clicked slot contains an item
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return; // Ignore if no item is clicked
        }
        Player player = (Player) event.getWhoClicked();
        if(event.getInventory().getViewers().isEmpty())  {
            return;
        }
        if(findGUIWherePlayerIsLooking(player) != null) {
            findGUIWherePlayerIsLooking(player).onClick(clickedItem, player);
            event.setCancelled(true);
        }
    }

    public static void openNewGUI(String type, Player player) {
    }

    public static void openNewGUI(String type, Player player, Board board) {
        switch (type) {
            case "leaderboard":
                new LeaderboardGUI("Leaderboard Menu", board).openForPlayer(player);
                break;
        }
    }

    public static BaseGUI findGUIWherePlayerIsLooking(Player player) {
        for (BaseGUI baseGUI : GUIs) {
            if (!baseGUI.inventory.getViewers().isEmpty() && baseGUI.inventory.getViewers().contains(player)) {
                return baseGUI;
            }
        }
        return null;
    }

    public static BaseGUI findGUIById(String Id) {
        for (BaseGUI baseGUI : GUIs) {
            if (baseGUI.Id.equals(Id)) {
                return baseGUI;
            }
        }
        return null;
    }
}
