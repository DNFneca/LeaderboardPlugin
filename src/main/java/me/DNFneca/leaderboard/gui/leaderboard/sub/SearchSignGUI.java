package me.DNFneca.leaderboard.gui.leaderboard.sub;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.comphenix.protocol.wrappers.nbt.*;
import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.component.ComponentUtils;
import me.DNFneca.leaderboard.utils.gui.GUIManager;
import me.DNFneca.leaderboard.utils.item.ItemUtils;
import me.DNFneca.leaderboard.utils.mob.MobUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

import static me.DNFneca.leaderboard.utils.gui.ProtocolLibHook.protocolManager;

public class SearchSignGUI {
    public static void openSearch(Player player, String IdOfInventory) {

        // Define a dummy block position (does not exist in the world)
        BlockPosition blockPosition = new BlockPosition((int) player.getLocation().getX(), (int) player.getLocation().getY() - 2, (int) player.getLocation().getZ());

        // Create the packet

        player.sendBlockChange(blockPosition.toLocation(player.getWorld()), Material.OAK_SIGN.createBlockData());
        Leaderboard.getInstance().log.info(String.valueOf(player.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).getType()));

        PacketContainer openSign = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        PacketContainer signData = protocolManager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

        openSign.getBlockPositionModifier().write(0, blockPosition);

        NbtCompound signNBT = (NbtCompound) signData.getNbtModifier().read(0);
        NbtCompound frontText = NbtFactory.ofCompound("front_text");
        NbtCompound value = NbtFactory.ofCompound("");
        NbtList<String> messages = NbtFactory.ofList("messages");
        messages.add("\"\"");
        messages.add("\"\"");
        messages.add("\"\"");
        messages.add(ComponentUtils.serializeComponent(Component.text("Search").color(TextColor.fromHexString("#fff"))));
        value.put(messages);
        frontText.put(messages);
        signNBT.put(frontText);

        Leaderboard.getInstance().log.info(GUIManager.findGUIById(IdOfInventory).getClass().getName().replace("me.DNFneca.leaderboard.gui.leaderboard.sub.", ""));
        player.getPersistentDataContainer().set(new NamespacedKey(Leaderboard.getInstance(), "nextGUI"), PersistentDataType.STRING, "ListGUI|" + GUIManager.findGUIById(IdOfInventory).getClass().getName().replace("me.DNFneca.leaderboard.gui.leaderboard.sub.", ""));
        player.getPersistentDataContainer().set(new NamespacedKey(Leaderboard.getInstance(), "lastGUI"), PersistentDataType.STRING, IdOfInventory);
        Leaderboard.getInstance().log.info(String.valueOf(WrappedRegistrable.blockEntityType("sign").getKey()));

        signData.getBlockPositionModifier().write(0, blockPosition);
        signData.getBlockEntityTypeModifier().write(0, WrappedRegistrable.blockEntityType("sign"));
        signData.getNbtModifier().write(0, signNBT);

        // Send the "open sign editor" packet
        PacketContainer openSignPacket = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        openSignPacket.getBlockPositionModifier().write(0, blockPosition);
        openSignPacket.getBooleans().write(0, true);

        protocolManager.sendServerPacket(player, signData);
        protocolManager.sendServerPacket(player, openSignPacket);
    }

    public static ArrayList<ItemStack> searchItems(String search) {
        ArrayList<ItemStack> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (ItemUtils.getFriendlyName(material).toLowerCase().contains(search.toLowerCase()) && material.isItem() && material != Material.AIR) {
                materials.add(ItemUtils.makeGUIItemOfType(material));
            }
        }
        return materials;
    }

    public static ArrayList<ItemStack> searchMobs(String search) {
        ArrayList<ItemStack> materials = new ArrayList<>();
        for (EntityType material : EntityType.values()) {
            if (MobUtils.getFriendlyName(material).toLowerCase().contains(search.toLowerCase()) && material.isAlive() && material != EntityType.PLAYER) {
                materials.add(ItemUtils.makeGUIItemOfType(Material.NAME_TAG, MobUtils.getFriendlyName(material)));
            }
        }
        return materials;
    }

    public static ArrayList<ItemStack> searchBlocks(String search) {
        ArrayList<ItemStack> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (ItemUtils.getFriendlyName(material).toLowerCase().contains(search.toLowerCase()) && material.isItem() && material.isBlock() && material != Material.AIR) {
                materials.add(ItemUtils.makeGUIItemOfType(material));
            }
        }
        return materials;
    }
}
