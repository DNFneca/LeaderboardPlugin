package me.DNFneca.leaderboard.listeners;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.gui.GUIManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class PlayerClickArmorStand implements Listener {
    @EventHandler
    public void onPlayerRightClickOnEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Leaderboard.getInstance().log.info(event.getRightClicked().getType().toString());
        if (event.getHand() == EquipmentSlot.HAND) {


            // If the ray hits a block
            Board board = Leaderboard.boardDB.boardsCollection.getBoardById(event.getRightClicked().getUniqueId().toString());
            player.sendMessage(board.getMainRow().getText());
            player.getPersistentDataContainer().set(new NamespacedKey(Leaderboard.getInstance(), "openedLeaderboard"), PersistentDataType.STRING, event.getRightClicked().getUniqueId().toString());
            if (board != null) {
                GUIManager.openNewGUI("leaderboard", player, board);
            }


            // Perform actions when a player right-clicks a sheep
        }
    }
}
