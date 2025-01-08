package me.DNFneca.leaderboard.utils.db;

import com.google.gson.Gson;
import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.board.Board;
import me.DNFneca.leaderboard.utils.board.BoardsCollection;

import java.io.*;
import java.util.ArrayList;

public class BoardDatabase {
    private String databaseFilePath;
    public BoardsCollection boardsCollection;
    public BoardDatabase(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
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
        loadData();
    }

    public Board getEntity(String id) {
        for (Board playerData : Leaderboard.boardDB.boardsCollection.getBoardList()) {
            if (playerData.getId().equals(id)) {
                return playerData;
            }
        }
        return null;
    }

    public void saveData() {
        try (Writer writer = new FileWriter(databaseFilePath)) {
            Gson gson = new Gson();
            gson.toJson(boardsCollection, BoardsCollection.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadData() {
        try (Reader reader = new FileReader(databaseFilePath)) {
            Gson gson = new Gson();
            boardsCollection = gson.fromJson(reader, BoardsCollection.class);
            if(boardsCollection == null) boardsCollection = new BoardsCollection(new ArrayList<>());
            Leaderboard.getInstance().log.info(String.valueOf(boardsCollection.getBoardList()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
