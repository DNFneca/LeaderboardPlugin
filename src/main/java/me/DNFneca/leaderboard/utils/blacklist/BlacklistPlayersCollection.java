package me.DNFneca.leaderboard.utils.blacklist;

import me.DNFneca.leaderboard.utils.db.DBCollection;

import java.util.List;

public class BlacklistPlayersCollection extends DBCollection<BlacklistPlayer> {
    public BlacklistPlayersCollection(List<BlacklistPlayer> collection) {
        super(collection);
    }
}
