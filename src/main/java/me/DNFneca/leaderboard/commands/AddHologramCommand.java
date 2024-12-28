package me.DNFneca.leaderboard.commands;

import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.board.BoardsCollection;
import me.DNFneca.leaderboard.utils.chat.MessageUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddHologramCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        player.sendMessage("About to start command");
        if (strings.length == 2 && strings[0].equalsIgnoreCase("current") && isStringNumber(strings[1])) {
            Board board = new Board(((Player) commandSender).getLocation(), "Deaths", Integer.parseInt(strings[1]));
            player.sendMessage("Board created");
            player.sendMessage("About to update boards");

            BoardsCollection.update();
            MessageUtils.SendMessageToPlayer((Player) commandSender, "Successfully added hologram!");
            return true;
        } else if(strings.length < 3) {
            return false;
        }
        Location location = new Location(((Player) commandSender).getWorld(), 0, 0, 0);


        if (strings[0] == "~") {
            location.setX(((Player) commandSender).getX() + .5);
        } else if (strings[0] == "current") {

        } else {
            location.setX(Math.floor(Double.parseDouble(strings[0])) + .5);
        }

        return true;
    }


    private boolean isStringNumber(String potentialNumber) {
        if (potentialNumber == null) return false;
        if (potentialNumber.isEmpty()) return false;
        if (potentialNumber.isBlank()) return false;
        try {
            Integer.parseInt(potentialNumber);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
