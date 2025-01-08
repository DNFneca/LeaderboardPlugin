package me.DNFneca.leaderboard.listeners;

import me.DNFneca.leaderboard.Leaderboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerIncrementStatistic implements Listener {
    @EventHandler
    public void onPlayerIncrementStatistic(PlayerStatisticIncrementEvent playerIncrementStatistic) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Leaderboard.boardDB.boardsCollection.update();

            }
        }.runTaskLater(Leaderboard.getInstance(), 1L);
    }
}
