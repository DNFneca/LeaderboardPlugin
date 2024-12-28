package me.DNFneca.leaderboard.commands;

import me.DNFneca.leaderboard.utils.chat.MessageUtils;
import me.DNFneca.leaderboard.utils.blacklist.BlacklistPlayer;
import me.DNFneca.leaderboard.utils.player.PlayerScore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AddPlayerToBlackList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        assert commandSender instanceof Player;
        Player player = (Player) commandSender;
        if (strings.length == 2 && Bukkit.getPlayer(strings[0]) != null && Bukkit.getPlayer(strings[1]) != null) {
            BlacklistPlayer blacklistPlayer = new BlacklistPlayer(Bukkit.getPlayer(strings[0]).getUniqueId().toString(), Bukkit.getPlayer(strings[1]).getUniqueId().toString(), new PlayerScore());
            MessageUtils.SendMessageToPlayer((Player) commandSender, "Successfully added hologram!");
            return true;
        } else if(strings.length < 3) {
            return false;
        }
        return false;
    }
}
