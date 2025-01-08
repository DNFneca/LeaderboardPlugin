package me.DNFneca.leaderboard.utils.db;

import me.DNFneca.leaderboard.utils.blacklist.BlacklistPlayer;
import me.DNFneca.leaderboard.utils.player.PlayerScore;

public class PlayerBlacklistDatabase extends Database<BlacklistPlayer> {
    private String attributedToId;
    private String id;


    public PlayerBlacklistDatabase(String filename) {
        super(filename);
    }

    public boolean doesEntityExist(String id) {
        update();
        if (getData() == null) return false;
        for (BlacklistPlayer data1 : getData().getCollection()) {
            if (data1.getId().equals(id)) return true;
        }
        return false;
    }

    public boolean addEntity(String id, String attributedToId, PlayerScore score) {
        if(doesEntityExist(id)) return false;
        getData().getCollection().add(new BlacklistPlayer(id, attributedToId, score));
        update();
        saveData();
        return true;
    }


}
