package me.DNFneca.leaderboard.utils.gui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.gui.leaderboard.sub.ListGUI;
import me.DNFneca.leaderboard.gui.leaderboard.sub.SearchSignGUI;
import me.DNFneca.leaderboard.utils.component.ComponentUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public final class ProtocolLibHook {

    public static ProtocolManager protocolManager;

    public static void register() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(Leaderboard.getInstance(), PacketType.Play.Server.OPEN_SIGN_EDITOR) {
            @Override
            public void onPacketSending(PacketEvent packetEvent) {
                Leaderboard.getInstance().log.info(String.valueOf(packetEvent.getPacket().getBooleans().read(0)));
                packetEvent.getPlayer().sendMessage(ComponentUtils.deserializeComponent("Sigma"));
            }
        });
        protocolManager.addPacketListener(new PacketAdapter(Leaderboard.getInstance(), PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                Leaderboard.getInstance().log.info(packetEvent.getPacket().getStringArrays().read(0)[1]);
                packetEvent.getPlayer().sendMessage(ComponentUtils.deserializeComponent("Sigma2"));
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "nextGUI"), PersistentDataType.STRING).equals("ListGUI|BlocksMinedGUI")) {
                            new ListGUI("Search Results", 27, SearchSignGUI.searchBlocks(packetEvent.getPacket().getStringArrays().read(0)[0])).setStatistic(Statistic.MINE_BLOCK).setStatisticType("Blocks").setParentGUI(GUIManager.findGUIById(player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "lastGUI"), PersistentDataType.STRING))).openForPlayer(player);
                        } else if (player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "nextGUI"), PersistentDataType.STRING).equals("ListGUI|BlocksPlacedGUI")) {
                            new ListGUI("Search Results", 27, SearchSignGUI.searchBlocks(packetEvent.getPacket().getStringArrays().read(0)[0])).setStatistic(Statistic.USE_ITEM).setStatisticType("Blocks").setParentGUI(GUIManager.findGUIById(player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "lastGUI"), PersistentDataType.STRING))).openForPlayer(player);
                        } else if (player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "nextGUI"), PersistentDataType.STRING).equals("ListGUI|KillsGUI")) {
                            new ListGUI("Search Results", 27, SearchSignGUI.searchMobs(packetEvent.getPacket().getStringArrays().read(0)[0])).setStatistic(Statistic.MOB_KILLS).setParentGUI(GUIManager.findGUIById(player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "lastGUI"), PersistentDataType.STRING))).openForPlayer(player);
                        } else if (player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "nextGUI"), PersistentDataType.STRING).equals("ListGUI|DeathsGUI")) {
                            new ListGUI("Search Results", 27, SearchSignGUI.searchMobs(packetEvent.getPacket().getStringArrays().read(0)[0])).setStatistic(Statistic.DEATHS).setParentGUI(GUIManager.findGUIById(player.getPersistentDataContainer().get(new NamespacedKey(Leaderboard.getInstance(), "lastGUI"), PersistentDataType.STRING))).openForPlayer(player);
                        }
                        player.sendBlockChange(packetEvent.getPacket().getBlockPositionModifier().read(0).toLocation(player.getWorld()), player.getWorld().getBlockData(packetEvent.getPacket().getBlockPositionModifier().read(0).toLocation(player.getWorld())));
                    }
                }.runTaskLater(Leaderboard.getInstance(), 2L);
            }
        });
        protocolManager.addPacketListener(new PacketAdapter(Leaderboard.getInstance(), PacketType.Play.Server.BLOCK_CHANGE) {
            @Override
            public void onPacketSending(PacketEvent packetEvent) {
                packetEvent.getPlayer().sendMessage(ComponentUtils.deserializeComponent("Sigma"));
                Player player = packetEvent.getPlayer();
            }
        });

//        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.BLOCK_ACTION);
//        packetContainer.getSectionPositions()
//                .write()
    }

    private static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


}
