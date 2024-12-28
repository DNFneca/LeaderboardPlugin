package me.DNFneca.leaderboard.commands;

import me.DNFneca.leaderboard.Leaderboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class reloadLeaderboardPlugin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("reloadlogic")) {
            if (sender.hasPermission("myplugin.reload")) { // Optional permission check
                sender.sendMessage("Reloading plugin logic...");
                Leaderboard.getInstance().reloadLogic();
                sender.sendMessage("Plugin logic reloaded!");
            } else {
                sender.sendMessage("You don't have permission to use this command!");
            }
            return true;
        }
        return false;
    }
}
