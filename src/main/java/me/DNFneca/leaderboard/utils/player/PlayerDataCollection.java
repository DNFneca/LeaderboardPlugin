package me.DNFneca.leaderboard.utils.player;

import me.DNFneca.leaderboard.utils.db.DBCollection;

import java.util.List;

public class PlayerDataCollection extends DBCollection<PlayerData> {
    public PlayerDataCollection(List<PlayerData> collection) {
        super(collection);
    }
}
