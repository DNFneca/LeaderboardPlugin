package me.DNFneca.leaderboard.utils.db;

import com.google.gson.Gson;
import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.board.BoardsCollection;
import me.DNFneca.leaderboard.utils.player.PlayerData;
import me.DNFneca.leaderboard.utils.player.PlayerDataCollection;
import me.DNFneca.leaderboard.utils.player.PlayerScore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PlayerScoreDatabase {
    private String databaseFilePath;
    public PlayerDataCollection entityScores;
    public PlayerScoreDatabase(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                Files.createDirectories(Path.of("./plugins/Leaderboard/"));
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("Failed to create file");
                } else {
                    System.out.println("File created successfully");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
        databaseFilePath = filePath;
        update();
    }

    public boolean addToEntityScore(String id, PlayerScore score) {
        if(!doesEntityExist(id)) return false;
        if(!doesScoreInDataExist(id, score.getScoreName())) {
            for (PlayerData entityData : entityScores.getCollection()) {
                if (entityData.getId().equals(id)) {
                    entityData.addScore(new PlayerScore(score.getScoreName(), "1"));
                }
            }
        } else {
            for (PlayerData entityData : entityScores.getCollection()) {
                for (PlayerScore entityScore : entityData.getScores()) {
                    if (entityScore.getScoreName().equals(score.getScoreName()) && entityData.getId().equals(id)) {
                        entityScore.setScore(String.valueOf(Integer.parseInt(entityScore.getScore()) + Integer.parseInt(score.getScore())));
                    }
                }
            }
        }
        BoardsCollection.update();
        saveData();
        return true;
    }

    public boolean addEntity(String id, String name, PlayerScore score) {
        if(doesEntityExist(id)) return false;

        entityScores.add(new PlayerData(id, name, score));
        BoardsCollection.update();
        saveData();
        return true;
    }

    public PlayerData getEntity(String id) {
        update();
        for (PlayerData entityData : entityScores.getCollection()) {
            if (entityData.getId().equals(id)) {
                return entityData;
            }
        }
        return null;
    }

    public void saveData() {
        try (Writer writer = new FileWriter(databaseFilePath)) {
            Gson gson = new Gson();
            gson.toJson(entityScores, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean doesEntityExist(String id) {
        update();
        if (entityScores.getCollection().isEmpty()) return false;
        for (PlayerData data1 : entityScores.getCollection()) {
            if (data1.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean doesScoreInDataExist(String id, String scoreName) {
        update();
        if (entityScores.getCollection().isEmpty()) return false;
        for (PlayerData data : entityScores.getCollection()) {
            if (data.getId().equals(id)) {
                for (PlayerScore entityScore : data.getScores()) {
                    if (entityScore.getScoreName().equals(scoreName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void update() {
        try (Reader reader = new FileReader(databaseFilePath)) {
            Gson gson = new Gson();
            entityScores = gson.fromJson(reader, PlayerDataCollection.class);
            if(entityScores == null) entityScores = new PlayerDataCollection(new ArrayList<>());
        } catch (FileNotFoundException e) {
            try {
                File file = new File(databaseFilePath);
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
