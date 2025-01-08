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

public class DeathsGUI extends BaseBoardGUI {

    public DeathsGUI(String title, List<ItemStack> items) {
        super(title, 27, items);
    }

    public DeathsGUI(String title) {
        super(title, 27);

        setItem(11, ItemUtils.makeGUIItemOfType(Material.GOLDEN_SWORD, "Total Deaths"));

        setItem(13, ItemUtils.makeGUIItemOfType(Material.DIAMOND_SWORD, "Total Deaths by Player"));

        setItem(15, ItemUtils.makeGUIItemOfType(Material.IRON_SWORD, "Specific Deaths"));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Deaths")) {
            getBoard().setStatistic(Statistic.DEATHS);
            getBoard().getMainRow().setText(Component.text("Total Player Deaths").color(TextColor.fromHexString("#00ffff")));
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Total Deaths by Player")) {
            getBoard().setStatistic(Statistic.ENTITY_KILLED_BY, EntityType.PLAYER);
            getBoard().getMainRow().setText(Component.text("Total Deaths by Player").color(TextColor.fromHexString("#00ffff")));
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Specific Deaths")) {
            SearchSignGUI.openSearch(player, this.Id);
        }
    }
}
