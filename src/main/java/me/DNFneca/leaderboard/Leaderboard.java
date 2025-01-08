package me.DNFneca.leaderboard;

import me.DNFneca.leaderboard.commandCompleters.AddHologramCompleter;
import me.DNFneca.leaderboard.commands.AddHologramCommand;
import me.DNFneca.leaderboard.commands.AddPlayerToBlackList;
import me.DNFneca.leaderboard.listeners.PlayerClickArmorStand;
import me.DNFneca.leaderboard.listeners.PlayerIncrementStatistic;
import me.DNFneca.leaderboard.utils.db.BoardDatabase;
import me.DNFneca.leaderboard.utils.db.PlayerBlacklistDatabase;
import me.DNFneca.leaderboard.utils.gui.GUIManager;
import me.DNFneca.leaderboard.utils.gui.ProtocolLibHook;
import me.DNFneca.leaderboard.utils.version.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.logging.Logger;

public final class Leaderboard extends JavaPlugin {
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


        ProtocolLibHook.register();

        boardDB = new BoardDatabase("./plugins/Leaderboard/boards.json");
        blacklistDB = new PlayerBlacklistDatabase("./plugins/Leaderboard/blacklists.json");

        Manager.Initialize();

        log.info(String.valueOf(EntityType.values().length));

        this.getServer().getPluginManager().registerEvents(new PlayerClickArmorStand(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerIncrementStatistic(), this);
        this.getServer().getPluginManager().registerEvents(new GUIManager(), this);


        this.getCommand("addPlayerToBlacklist").setExecutor(new @NonNull AddPlayerToBlackList());
        this.getCommand("addHologram").setExecutor(new @NonNull AddHologramCommand());
        this.getCommand("addHologram").setTabCompleter(new AddHologramCompleter());

//        BoardsCollection.update();



        log.info("Logic loaded.");
    }


    public void unloadLogic() {
        // Clean up resources (cancel tasks, unregister listeners, etc.)
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
