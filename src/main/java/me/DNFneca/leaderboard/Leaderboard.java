package me.DNFneca.leaderboard;

import com.google.common.reflect.Reflection;
import me.DNFneca.leaderboard.commandCompleters.AddHologramCompleter;
import me.DNFneca.leaderboard.commands.AddHologramCommand;
import me.DNFneca.leaderboard.commands.AddPlayerToBlackList;
import me.DNFneca.leaderboard.commands.reloadLeaderboardPlugin;
import me.DNFneca.leaderboard.listeners.PlayerDeathListener;
import me.DNFneca.leaderboard.listeners.PlayerJoinListener;
import me.DNFneca.leaderboard.listeners.PlayerKillListener;
import me.DNFneca.leaderboard.utils.board.BoardsCollection;
import me.DNFneca.leaderboard.utils.db.BoardDatabase;
import me.DNFneca.leaderboard.utils.db.PlayerBlacklistDatabase;
import me.DNFneca.leaderboard.utils.db.PlayerScoreDatabase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public final class Leaderboard extends JavaPlugin {
    public static PlayerScoreDatabase playerStats;
    public static BoardDatabase boardDB;
    public static PlayerBlacklistDatabase blacklistDB;
    public Logger log;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadLogic();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        unloadLogic();
    }

    public void loadLogic() {
        log = getLogger();
        playerStats = new PlayerScoreDatabase("./plugins/Leaderboard/playerStats.json");
        boardDB = new BoardDatabase("./plugins/Leaderboard/boards.json");
        blacklistDB = new PlayerBlacklistDatabase("./plugins/Leaderboard/blacklists.json");


        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerKillListener(), this);


        this.getCommand("addPlayerToBlacklist").setExecutor(new @NonNull AddPlayerToBlackList());
        this.getCommand("addHologram").setExecutor(new @NonNull AddHologramCommand());
        this.getCommand("addHologram").setTabCompleter(new AddHologramCompleter());
        this.getCommand("reloadLeaderboardPlugin").setExecutor(new reloadLeaderboardPlugin());

        BoardsCollection.update();
        log.info("Logic loaded.");
    }

    public void unloadLogic() {
        // Clean up resources (cancel tasks, unregister listeners, etc.)
        try {
            // Access the CommandMap
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Unregister all commands related to this plugin
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) knownCommandsField.get(commandMap);

            // Remove this plugin's commands
            knownCommands.entrySet().removeIf(entry -> entry.getValue() instanceof PluginCommand &&
                    ((PluginCommand) entry.getValue()).getPlugin() == this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        HandlerList.unregisterAll(this);

        log.info("Logic unloaded.");
    }

    public void reloadLogic() {
        unloadLogic(); // Clean up current state
        loadLogic();   // Reload logic
    }

    public static Leaderboard getInstance() {
        return (Leaderboard) Bukkit.getServer().getPluginManager().getPlugin("Leaderboard");
    }
}
