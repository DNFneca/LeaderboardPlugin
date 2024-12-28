package me.DNFneca.leaderboard.listeners;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.player.PlayerScore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(!Leaderboard.playerStats.doesEntityExist(p.getUniqueId().toString())) {
            Leaderboard.playerStats.addEntity(p.getUniqueId().toString(), p.getName(), new PlayerScore(e.getEventName(), "1"));
        } else {
            Leaderboard.playerStats.addToEntityScore(p.getUniqueId().toString(), new PlayerScore(e.getEventName(), "1"));
        }
    }
}
