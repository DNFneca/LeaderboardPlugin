package me.DNFneca.leaderboard.commandCompleters;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddHologramCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        assert commandSender instanceof Player;
        ArrayList<String> list = new ArrayList<>();
        Block block = ((Player) commandSender).getTargetBlockExact(4);
        if (strings.length == 1) {
            list.add("current");
            list.add("~ ~ ~");
            list.add("~ ~");
            list.add("~");
            if(block == null) return list;
            list.add(block.getX() + " " + block.getY() + " " + block.getZ());
            list.add(block.getX() + " " + block.getY());
            list.add(String.valueOf(block.getX()));
        } else if (strings.length == 2 && !strings[0].equals("current")) {
            list.add("~ ~");
            list.add("~");
            if(block == null) return list;
            list.add(block.getY() + " " + block.getZ());
            list.add(String.valueOf(block.getY()));
        } else if (strings.length == 3 && !strings[0].equals("current")) {
            list.add("~");
            if(block == null) return list;
            list.add(String.valueOf(block.getZ()));
        }
        return list;
    }
}
