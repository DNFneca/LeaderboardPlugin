package me.DNFneca.leaderboard.listeners;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.player.PlayerScore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillListener implements Listener {
    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player player) {
            if(!Leaderboard.playerStats.doesEntityExist(player.getUniqueId().toString())) {
                Leaderboard.playerStats.addEntity(player.getUniqueId().toString(), player.getName(), new PlayerScore(e.getEventName(), "1"));
            } else {
                Leaderboard.playerStats.addToEntityScore(player.getUniqueId().toString(), new PlayerScore(e.getEventName(), "1"));
            }
        }
    }
}
