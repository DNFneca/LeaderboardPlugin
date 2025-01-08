package me.DNFneca.leaderboard.utils.gui;

import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.DNFneca.leaderboard.utils.gui.GUIManager.GUIs;

public class BaseGUI {
    public String Id;
    public String title;
    public String eventTitle;
    public BaseGUI parentGUI;
    public int size;
    public Inventory inventory;

    public BaseGUI(String title, int size, List<ItemStack> items) {
        this.title = title;
        this.size = size;
        Id = UUID.randomUUID().toString();
        inventory = Bukkit.createInventory(null, size, title);
        init();
        setInventoryItems(items);
        GUIs.add(this);
    }

    public BaseGUI(String title, int size) {
        this.title = title;
        this.size = size;
        Id = UUID.randomUUID().toString();
        inventory = Bukkit.createInventory(null, size, title);
        init();
        GUIs.add(this);
    }

    private void init() {
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(""));
        itemStack.setItemMeta(itemMeta);
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, itemStack);
        }
    }

    public void setItem(int i, ItemStack item) {
        if(i > size) return;
        inventory.setItem(i, item);
    }

    public BaseGUI setParentGUI(BaseGUI parentGUI) {
        setItem(size - 9, ItemUtils.makeGUIItemOfType(Material.ARROW, "Back"));
        this.parentGUI = parentGUI;
        return this;
    }

    public BaseGUI getParentGUI() {
        return parentGUI;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void openForPlayer(Player player) {
        player.openInventory(inventory);
    }

    public void setInventoryItems(List<ItemStack> items) {
        for (int i = 0; i < size; i++) {
            if (items.get(i) != inventory.getItem(i)) {
                inventory.setItem(i, items.get(i));
            }
        }
    }

    public void onClick(ItemStack itemStack, Player player) {
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Back")) {
            back(player);
        }
    }

    public void back(Player player) {
        if (parentGUI == null) return;
        parentGUI.openForPlayer(player);

    }

    public void closeGUI() {
        this.inventory.close();
        GUIs.remove(this);
    }
}
