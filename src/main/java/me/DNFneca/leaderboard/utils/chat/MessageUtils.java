package me.DNFneca.leaderboard.utils.chat;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.awt.*;

public class MessageUtils {
    public static void SendMessageToPlayer(Player player, String message) {
        Component ms = Component.text("[")
                .color(NamedTextColor.GOLD)
                .append(Component.text("Leaderboard").color(NamedTextColor.AQUA))
                .append(Component.text("] ").color(NamedTextColor.GOLD))
                .append(Component.text(message));
        player.sendMessage(ms);

    }
}
