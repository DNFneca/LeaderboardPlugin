package me.DNFneca.leaderboard.gui.leaderboard.sub;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.component.ComponentUtils;
import me.DNFneca.leaderboard.utils.gui.BaseBoardGUI;
import me.DNFneca.leaderboard.utils.gui.BaseGUI;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import me.DNFneca.leaderboard.utils.mob.MobUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.stats.Stat;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ListGUI extends BaseBoardGUI {
    List<ItemStack> show = new ArrayList<>();
    Material statisticMaterial;
    EntityType statisticEntityType;
    Statistic statistic;
    String statisticType;

    public ListGUI(String title, int size, ArrayList<ItemStack> items) {
        super(title, size);
        show.addAll(items);
        if (show.size() > 18) {
            setItem(26, ItemUtils.makeGUIItemOfType(Material.ARROW, "Next"));
        }
        for (int i = 0; i < items.size() && i <= 17; i++) {
            setItem(i, items.get(i));
        }
        setItem(size - 5, ItemUtils.makeGUIItemOfType(Material.BARRIER, "Cancel"));
    }


    @Override
    public void onClick(ItemStack itemStack, Player player) {
        super.onClick(itemStack, player);
        if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Next")) {
            next(player);
        } else if (ItemUtils.isGUIItemWithSpecificName(itemStack, "Cancel")) {
            BaseGUI baseGUI = this;
            while (baseGUI instanceof ListGUI) {
                baseGUI = baseGUI.parentGUI;
            }
            baseGUI.openForPlayer(player);
        } else if (ComponentUtils.areComponentsEqual(Component.text(ItemUtils.getFriendlyName(itemStack.getType())).color(TextColor.fromHexString("#ffffff")).decoration(TextDecoration.ITALIC, false), itemStack.displayName())) {
            Board board = Leaderboard.boardDB.boardsCollection.getBoardById(player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "openedLeaderboard"), PersistentDataType.STRING));
            if (statistic == Statistic.MINE_BLOCK && statisticType.equals("Blocks")) {
                board.setStatistic(Statistic.MINE_BLOCK, itemStack.getType());
                board.setStatisticType(statisticType);
                board.getMainRow().setText(Component.text(ItemUtils.getFriendlyName(itemStack.getType()) + " Mined").color(TextColor.fromHexString("#00ffff")));
            } else if (statistic == Statistic.USE_ITEM && statisticType.equals("Blocks")) {
                board.setStatistic(Statistic.USE_ITEM, itemStack.getType());
                board.setStatisticType(statisticType);
                board.getMainRow().setText(Component.text(ItemUtils.getFriendlyName(itemStack.getType()) + " Placed").color(TextColor.fromHexString("#00ffff")));
            }
            BaseGUI baseGUI = this;
            while (baseGUI instanceof ListGUI) {
                baseGUI = baseGUI.parentGUI;
            }
            baseGUI.setItem(13, itemStack);
            baseGUI.openForPlayer(player);
        } else if (MobUtils.getEntityType(PlainTextComponentSerializer.plainText().serialize(itemStack.displayName()).replace("[", "").replace("]", "")) != null) {
            if (statistic == Statistic.MOB_KILLS) {
                getBoard().setStatistic(Statistic.KILL_ENTITY, MobUtils.getEntityType(PlainTextComponentSerializer.plainText().serialize(itemStack.displayName()).replace("[", "").replace("]", "")));
                getBoard().getMainRow().setText(Component.text(MobUtils.getFriendlyName(MobUtils.getEntityType(PlainTextComponentSerializer.plainText().serialize(itemStack.displayName()).replace("[", "").replace("]", ""))) + "s Killed").color(TextColor.fromHexString("#00ffff")));
            } else if (statistic == Statistic.DEATHS) {
                getBoard().setStatistic(Statistic.ENTITY_KILLED_BY, MobUtils.getEntityType(PlainTextComponentSerializer.plainText().serialize(itemStack.displayName()).replace("[", "").replace("]", "")));
                getBoard().getMainRow().setText(Component.text("Total Times Killed by " +  MobUtils.getFriendlyName(MobUtils.getEntityType(PlainTextComponentSerializer.plainText().serialize(itemStack.displayName()).replace("[", "").replace("]", "")))).color(TextColor.fromHexString("#00ffff")));
            }
        }
    }

    @Override
    public ListGUI setParentGUI(BaseGUI parentGUI) {
        if (parentGUI instanceof ListGUI) {
            return (ListGUI) super.setParentGUI(parentGUI);
        }
        this.parentGUI = parentGUI;
        return this;
    }

    public ListGUI setStatisticType(String statisticType) {
        this.statisticType = statisticType;
        return this;
    }

    public String getStatisticType() {
        return statisticType;
    }

    public ListGUI setStatistic(Statistic statistic) {
        this.statistic = statistic;
        this.statisticMaterial = null;
        this.statisticEntityType = null;
        this.statisticType = "";
        return this;
    }

    public ListGUI setStatistic(Statistic statistic, Material statisticMaterial) {
        this.statistic = statistic;
        this.statisticMaterial = statisticMaterial;
        this.statisticEntityType = null;
        this.statisticType = "";
        return this;
    }

    public ListGUI setStatistic(Statistic statistic, EntityType statisticEntityType) {
        this.statistic = statistic;
        this.statisticEntityType = statisticEntityType;
        this.statisticMaterial = null;
        this.statisticType = "";
        return this;
    }

    public ListGUI setStatistic(Statistic statistic, Material statisticMaterial, EntityType statisticEntityType) {
        this.statistic = statistic;
        this.statisticEntityType = statisticEntityType;
        this.statisticMaterial = statisticMaterial;
        this.statisticType = "";
        return this;
    }

    public void next(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < show.size(); i++) {
            if (i > 17) {
                items.add(show.get(i));
            }
        }
        new ListGUI("Search Results", 27, items).setParentGUI(this).openForPlayer(player);
    }
}
