package me.DNFneca.leaderboard.utils.blacklist;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.player.PlayerScore;

import java.util.ArrayList;
import java.util.List;

public class BlacklistPlayer {
    private String attributeToId;
    private String id;
    private String name;
    List<PlayerScore> scores = new ArrayList<>();
    public BlacklistPlayer(String id, String attributeToId, List<PlayerScore> scores) {
        this.id = id;
        this.name = name;
        this.attributeToId = attributeToId;
        this.scores = scores;
    }

    public void addScore(PlayerScore score) {
        scores.add(score);
    }

    public BlacklistPlayer(String id, String attributeToId, PlayerScore scores) {
        this.id = id;
        this.name = name;
        this.attributeToId = attributeToId;
        this.scores.add(scores);
        Leaderboard.blacklistDB.saveData();
    }

    public String getId() {
        return id;
    }

    public String getAttributeToId() {
        return attributeToId;
    }

    public String getName() {
        return name;
    }

    public List<PlayerScore> getScores() {
        return scores;
    }

    public PlayerScore getScore(String scoreName) {
        for (PlayerScore score : scores) {
            if (score.getScoreName().equals(scoreName)) {
                return score;
            }
        }
        return null;
    }

    public void editScore(String scoreName, PlayerScore score) {
        for (PlayerScore score_ : scores) {
            if (score_.getScoreName() == scoreName) {
                score_ = score;
            }
        }
        this.scores = scores;
    }
}
