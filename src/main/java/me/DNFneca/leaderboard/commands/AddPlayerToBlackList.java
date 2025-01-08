package me.DNFneca.leaderboard.commands;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.chat.MessageUtils;
import me.DNFneca.leaderboard.utils.blacklist.BlacklistPlayer;
import me.DNFneca.leaderboard.utils.player.PlayerScore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SignBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AddPlayerToBlackList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        assert commandSender instanceof Player;
        Player player = (Player) commandSender;
        player.openInventory(Bukkit.createInventory(null, 54-9, "Leaderboard"));
        return true;
    }
}
