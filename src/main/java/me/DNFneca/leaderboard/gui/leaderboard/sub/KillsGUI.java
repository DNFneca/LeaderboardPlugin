package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KillsGUI extends BaseBoardGUI {

    public KillsGUI(String title, List<ItemStack> items) {
        super(title, 27, items);
    }

    public KillsGUI(String title) {
        super(title, 27);
        setItem(11, ItemUtils.makeGUIItemOfType(Material.DIAMOND_SWORD, "Player Kills"));
        setItem(13, ItemUtils.makeGUIItemOfType(Material.GOLDEN_SWORD, "Total Mob Kills"));
        setItem(15, ItemUtils.makeGUIItemOfType(Material.ZOMBIE_HEAD, "Specific Mob Kills"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Player Kills")) {
            getBoard().setStatistic(Statistic.PLAYER_KILLS);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Mob Kills")) {
            getBoard().setStatistic(Statistic.MOB_KILLS);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Specific Mob Kills")) {
            SearchSignGUI.openSearch(player, this.Id);
//            getBoard().setStatistic(Statistic.KILL_ENTITY);
        }
    }
}
