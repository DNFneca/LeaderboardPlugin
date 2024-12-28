package me.DNFneca.leaderboard.utils.player;


import me.DNFneca.leaderboard.Leaderboard;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private String id;
    private String name;
    List<PlayerScore> scores = new ArrayList<>();
    public PlayerData(String id, String name, List<PlayerScore> scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public void addScore(PlayerScore score) {
        scores.add(score);
    }

    public PlayerData(String id, String name, PlayerScore scores) {
        this.id = id;
        this.name = name;
        this.scores.add(scores);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PlayerScore> getScores() {
        return scores;
    }

    public PlayerScore getScore(String scoreName) {
        if (Leaderboard.playerStats.entityScores.getCollection().isEmpty()) return null;
        for (PlayerScore score : scores) {

            if (score.getScoreName().equals(scoreName)) {
                return score;
            }
        }
        return null;
    }

    public void editScore(String scoreName, PlayerScore score) {
        for (PlayerScore score_ : scores) {
            if (score_.getScoreName().equals(scoreName)) {
                score_ = score;
            }
        }
        this.scores = scores;
    }
}
