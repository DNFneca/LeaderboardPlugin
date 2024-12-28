package me.DNFneca.leaderboard.listeners;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.player.PlayerScore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
    }
}
