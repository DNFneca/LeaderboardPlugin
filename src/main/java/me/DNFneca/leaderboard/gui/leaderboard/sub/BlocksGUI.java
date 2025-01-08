package me.DNFneca.leaderboard.gui.leaderboard.sub;

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

public class BlocksGUI extends BaseBoardGUI {
    public BlocksGUI(String title, List<ItemStack> items) {
        super(title, 27, items);
    }

    public BlocksGUI(String title) {
        super(title, 27);

        setItem(11, ItemUtils.makeGUIItemOfType(Material.GRASS_BLOCK, "Placed Blocks"));

        setItem(15, ItemUtils.makeGUIItemOfType(Material.STONE_PICKAXE, "Mined Blocks"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if(ItemUtils.isGUIItemWithSpecificName(itemStack, "Mined Blocks")) {
            if (this.getBoard().getStatistic() == Statistic.MINE_BLOCK && getBoard().getStatisticType() != null && this.getBoard().getStatisticType().equals("Blocks")) {
                new BlocksMinedGUI("Mined Blocks Leaderboard", 27, getBoard().getStatisticMaterial()).setParentGUI(this).openForPlayer(player);
            } else {
                new BlocksMinedGUI("Mined Blocks Leaderboard", 27).setParentGUI(this).openForPlayer(player);
            }
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Placed Blocks")) {
            if (this.getBoard().getStatistic() == Statistic.USE_ITEM && getBoard().getStatisticType() != null && this.getBoard().getStatisticType().equals("Blocks")) {
                new BlocksPlacedGUI("Placed Blocks Leaderboard", 27, getBoard().getStatisticMaterial()).setParentGUI(this).openForPlayer(player);
            } else {
                new BlocksPlacedGUI("Mined Blocks Leaderboard", 27).setParentGUI(this).openForPlayer(player);
            }
        }
    }
}
