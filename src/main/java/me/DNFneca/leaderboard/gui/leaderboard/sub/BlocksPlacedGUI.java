package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static me.DNFneca.leaderboard.utils.component.ComponentUtils.areComponentsEqual;

public class BlocksPlacedGUI extends BaseBoardGUI {

    public BlocksPlacedGUI(String title, int size, List<ItemStack> items) {
        super(title, size, items);
    }

    public BlocksPlacedGUI(String title, int size) {
        super(title, size);

        setItem(4, ItemUtils.makeGUIItemOfType(Material.STONE_PICKAXE, "Mined Blocks"));

        setItem(12, ItemUtils.makeGUIItemOfType(Material.OAK_SIGN, "Search Blocks"));

        setItem(13, ItemUtils.makeGUIItemOfType(Material.BARRIER, "No Block Selected"));
    }

    public BlocksPlacedGUI(String title, int size, Material material) {
        super(title, size);

        setItem(4, ItemUtils.makeGUIItemOfType(Material.GRASS_BLOCK, "Mined Blocks"));

        setItem(12, ItemUtils.makeGUIItemOfType(Material.OAK_SIGN, "Search Blocks"));

        setItem(13, ItemUtils.makeGUIItemOfType(material));
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Search Blocks")) {
            SearchSignGUI.openSearch(player, this.Id);
        }
    }
}
