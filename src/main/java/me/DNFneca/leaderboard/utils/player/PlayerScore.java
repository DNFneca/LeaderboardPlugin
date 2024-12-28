package me.DNFneca.leaderboard.utils.player;

public class PlayerScore {
    private String scoreName;
    private String score;
    public PlayerScore(String scoreName, String score) {
        this.scoreName = scoreName;
        this.score = score;
    }

    public PlayerScore() {}

    public String getScore() {
        return score;
    }
    public String getScoreName() {
        return scoreName;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }
}
